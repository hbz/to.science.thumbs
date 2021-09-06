package de.hbznrw.thumby;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import de.hbznrw.thumby.controller.MainController;
import de.hbznrw.thumby.model.Storage;
import de.hbznrw.thumby.service.ThumbyService;

/**
*
* @author Jan Schnasse & Alessio Pellerito
*
* Integration test for the controller
*
*/
@WebMvcTest(MainController.class)
public class MainControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ThumbyService thumbyService;
	
	@MockBean
	private Storage storage;
	
	@Test
	void testStartPageView() throws Exception {
		
	        this.mockMvc.perform(
	        		get("/tools/thumby").contentType(MediaType.TEXT_HTML).content("upload"))
	          			.andExpect(status().isOk());
	}
}
