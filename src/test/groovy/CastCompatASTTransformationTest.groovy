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
