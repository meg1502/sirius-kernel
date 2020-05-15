/*
 * Made with all the love in the world
 * by scireum in Remshalden, Germany
 *
 * Copyright by scireum GmbH
 * http://www.scireum.de - info@scireum.de
 */

package sirius.kernel;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import sirius.kernel.commons.Value;

/**
 * Checks wether the test extended with this condition should be executed according to the given scope of the test
 * run. Only when all or nightly tests are explicilitly executed the test will be run.
 */
public class RunNightlyCondition implements ExecutionCondition {
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        boolean shouldRunNightly = Value.of(System.getProperty("test.nightly"))
                                        .asBoolean(Value.of(System.getProperty("test.all")).asBoolean());
        if (shouldRunNightly) {
            return ConditionEvaluationResult.enabled("Test enabled");
        }
        return ConditionEvaluationResult.disabled("Test only enabled in nightly scope");
    }
}
