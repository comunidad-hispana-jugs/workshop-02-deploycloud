package demo.service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.domain.DcVsMarvel;
import demo.domain.Heroe;
import rx.Observable;

/**
 * Build a sentence by assembling randomly generated heroes. The individual parts of speech will be obtained by
 * calling the various DAOs.
 * 
 * @author Ken Krueger, Jorge Centeno Fernandez 
 */
@Service
public class DcVsMarvelServiceImpl implements DcVsMarvelService {

	@Autowired HeroeService heroeService;

	/**
	 * Assemble a sentence by gathering random words of each part of speech:
	 */
	public String buildSentence() {
		DcVsMarvel sentence = new DcVsMarvel();
		
		//	Launch calls to all child services, using Observables 
		//	to handle the responses from each one:
		List<Observable<Heroe>> observables = createObservables();
		
		//	Use a CountDownLatch to detect when ALL of the calls are complete:
		CountDownLatch latch = new CountDownLatch(observables.size());
		
		//	Merge the 5 observables into one, so we can add a common subscriber:
		Observable.merge(observables)
			.subscribe(
				//	(Lambda) When each service call is complete, contribute its word
				//	to the sentence, and decrement the CountDownLatch:
				(heroe) -> {
					sentence.add(heroe);
					latch.countDown();
		        }
		);
		
		//	This code will wait until the LAST service call is complete:
		waitForAll(latch);

		//	Return the completed sentence:
		return sentence.toString();
	}


	/**
	 * Ultimately, we will need to wait for all 5 calls to 
	 * be completed before the sentence can be assembled.  
	 * This code waits for the last call to come back:
	 */
	private void waitForAll(CountDownLatch latch) {
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This code launches calls to all 5 child services, 
	 * using Observables to monitor the completion:
	 */
	private List<Observable<Heroe>> createObservables(){
		List<Observable<Heroe>> observables = new ArrayList<>();
		observables.add(heroeService.getDc());

		observables.add(heroeService.getMarvel());
		return observables;
	}
	
}
