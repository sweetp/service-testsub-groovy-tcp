package org.hoschi.sweetp.services.testsub

import groovy.util.logging.Log4j
import org.hoschi.sweetp.services.base.tcp.groovy.TcpService

/**
 * Test service to show subscription to a hook.
 *
 * @author Stefan Gojan
 */
@Log4j
class TestSub extends TcpService {
	static void main(String[] args) throws Exception {
		def port = System.getenv('PORT')
		assert port
		assert port.isInteger()

		TestSub own = new TestSub()
		own.connect('localhost', new Integer(port))
		own.listen()
	}

	@Override
	List getConfig(Map params) {
		[
				['/tests/service/testsub/wrapper': [
						method: 'wrapper',
						params: [
								text: 'one'
						],
						description: [
								summary: 'This is just a test for the main server. It provides a wrapper for the test hook "testpub/sayhello/pre" and post.',
								example: 'sweetp tests service testsub sayhello'
						],
						hooks: [
								sub: ['/testpub/sayhello/pre',
										'/testpub/sayhello/post']
						],
				]]
		]
	}

	/**
	 * Wrap incoming text.
	 *
	 * @param params contain text
	 * @return wrapped text
	 */
	Map wrapper(Map params) {
		[
				allOk: true,
				text: "original text was: $params.text"
		]
	}
}
