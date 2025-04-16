/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import groovy.test.GroovyTestCase

class CastCompatASTTransformationTest extends GroovyTestCase {
    void testThatAuthorExists() {
        def a = 3
        def b = (a) +1
        assert b == 4
    }

    void testASTTransformationShouldBeDebuggableFromIDE() {
        assertScript '''
            def a = 3
            def b = (a) -1
            assert b == 2
        '''
    }
}
