package demo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.mockito.Mockito;

import demo.dao.MarvelClient;
import demo.dao.DcClient;
import demo.domain.Heroe;

public class DcVsMarvelServiceImplTest {

	//	Class under test:
	DcVsMarvelServiceImpl service;
	
	@Before
	public void setup() {

		service = new DcVsMarvelServiceImpl();
		
		//	Establish Mock Dependencies:
		DcClient dc = Mockito.mock(DcClient.class);	
		MarvelClient marvel = Mockito.mock(MarvelClient.class);
		

		service.setDcService(dc);
		service.setMarvelService(marvel);

		//	Describe Mock Behaviors:
		Mockito.when(dc.getHeroe()).thenReturn(new Heroe("Batman"));
		Mockito.when(marvel.getHeroe())	.thenReturn(new Heroe("Iron Man"));

	}
	
	@Test
	public void test() {
		//	We should get the sentence built in the correct order:
		Assert.assertEquals("Batman Iron Man.", service.buildSentence());
	}

}
