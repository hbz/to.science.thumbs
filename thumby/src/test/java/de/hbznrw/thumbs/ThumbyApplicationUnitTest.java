package de.hbznrw.thumbs;

import java.io.File;
import java.io.FileInputStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.net.MediaType;

import de.hbznrw.thumbs.service.ThumbsService;

/**
*
* @author Jan Schnasse & Alessio Pellerito
*
* Simple (JUnit) tests that can call all parts of a Play app. If you are
* interested in mocking a whole application, see the wiki for more details.
*
*/
@SpringBootTest
class ThumbyApplicationUnitTest {
	
	private static final String HASH_OF_DEFAULT_DOWNLOAD_PIC = "be3a05c46c897b3e41c895d1fe025feaaf49fb2110e56489bf743bb8697a0b09";
	private static final Logger log = LoggerFactory.getLogger(ThumbyApplicationUnitTest.class);
	
	@Autowired
	ThumbsService thumbyService;
	
	@Test
	void getThumbnailUnitTest() 
	{	
		assertEquals("1c3d04b41bd081322a71efe0d76e47fa02403cd72f4886d5de3991656928576c", 
					 createThumbnailTest(new ClassPathResource("test.jpg").getFilename(), MediaType.JPEG));
		assertEquals("e476ac7a2d81a3df16fe21a41d616dc6a9c54c4adf750c74c3fc91e85adebe99", 
				 createThumbnailTest(new ClassPathResource("test.png").getFilename(), MediaType.PNG));
		assertEquals("1eb38b0b394916cc8c23a9a7a3d75a909a2e122f0082a52626125f608ff7cc59", 
				 createThumbnailTest(new ClassPathResource("test.zip").getFilename(), MediaType.ZIP));
		assertEquals(HASH_OF_DEFAULT_DOWNLOAD_PIC, 
				 createThumbnailTest(new ClassPathResource("test.ods").getFilename(), MediaType.OPENDOCUMENT_SPREADSHEET));
		assertEquals("0781abd674e0255d95c0c2a149038877e068e87ef722568989c3904f7ec5d55d", 
				 createThumbnailTest(new ClassPathResource("test.pdf").getFilename(), MediaType.PDF));
		assertEquals("fbcf35f9ec4705cc932bc0e0b70d0fa856c65349c1dfa74ba30c386f7aab558d", 
				 createThumbnailTest(new ClassPathResource("test.xcf").getFilename(), MediaType.ANY_IMAGE_TYPE));
		assertEquals("827340d3b69ae0c63f87c811e3d0a3504208e0279af15d439531800be392d50c", 
				 createThumbnailTest(new ClassPathResource("test.gif").getFilename(), MediaType.GIF));
		assertEquals("742c504be4d876cdfc65164e9d44eb59eb91c77eb793a345e05f16dcc38e2cbf", 
				 createThumbnailTest(new ClassPathResource("test.docx").getFilename(), MediaType.OOXML_DOCUMENT));	
	}
	
	private String createThumbnailTest(String name, MediaType mediaType) 
	{
		try 
		{
			File file = thumbyService.createThumbnail(
						new FileInputStream(
						new File(
						new ClassPathResource(name).getURI().getPath())), mediaType, 200, name);
			HashCode hashCode = Files.asByteSource(file).hash(Hashing.sha256());
			log.info("Create Test Data at " + file.getAbsolutePath() + " Hashcode: " + hashCode);
			return hashCode.toString();
			
		} catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
	} 
}
