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

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.CastExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

import static org.codehaus.groovy.ast.tools.GeneralUtils.plusX
import static org.codehaus.groovy.ast.tools.GeneralUtils.varX

@GroovyASTTransformation(phase= CompilePhase.CONVERSION)
class CastCompatASTTransformation extends AbstractASTTransformation {
    @Override
    void visit(final ASTNode[] nodes, final SourceUnit source) {
        def transformer = new ClassCodeExpressionTransformer() {
            @Override
            protected SourceUnit getSourceUnit() {
                return source
            }

            @Override
            Expression transform(Expression expr) {
                if (expr instanceof CastExpression) {
                    def name = expr.type.name
                    if (name.charAt(0).isLowerCase() && !name.contains('.') && name != 'int') {
                        def next = expr.expression
                        if (next instanceof ConstantExpression) {
                            def val = next.value
                            if (val instanceof Number) {
                                return plusX(varX(name), next)
                            }
                        }
                    }
                }
                return super.transform(expr)
            }
        }
        for (ClassNode node: source.getAST().getClasses()) {
            transformer.visitClass(node)
        }
    }
}
