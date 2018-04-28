package demo.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import demo.dao.DcDaoImpl;
import demo.dao.HeroeDao;
import demo.dao.MarvelDaoImpl;
import demo.domain.Heroe;

public class DcVsMarvelServiceImplTest {

	//	Class under test:
	DcVsMarvelServiceImpl service;
	
	@Before
	public void setup() {

		service = new DcVsMarvelServiceImpl();
		
		//	Establish Mock Dependencies:
		HeroeDao dc = Mockito.mock(DcDaoImpl.class);
		HeroeDao marvel = Mockito.mock(MarvelDaoImpl.class);

		service.setMarvelService(marvel);
		service.setDcService(dc);
		
		//	Describe Mock Behaviors:
		Mockito.when(marvel.getHeroe()).thenReturn(new Heroe("Iron Man"));
		Mockito.when(dc.getHeroe())	.thenReturn(new Heroe("Batman"));
	}
	
	@Test
	public void test() {
		//	We should get the sentence built in the correct order:
		Assert.assertEquals("Iron Man Batman.", service.buildSentence());
	}

}
