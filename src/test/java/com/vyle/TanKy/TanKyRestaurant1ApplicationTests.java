// test package need to be in the same level. Ex: it was com.vyle in test package, but in main it was com.vyle.TanKy. 
// Just fix test package to com.vyle.TanKy and test passes

// To see the code coverage, target/jacoco-resource/intdex.html -> open with web browser
package com.vyle.TanKy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TanKyRestaurant1ApplicationTests {

	@Test
	void contextLoads() {
	}

}
