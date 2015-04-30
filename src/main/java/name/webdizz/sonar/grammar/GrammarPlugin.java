package name.webdizz.sonar.grammar;

import name.webdizz.sonar.grammar.issue.tracking.GrammarActionDefinition;
import name.webdizz.sonar.grammar.issue.tracking.LinkFunction;
import name.webdizz.sonar.grammar.profile.GrammarProfileDefinition;
import name.webdizz.sonar.grammar.rule.GrammarRulesDefinition;
import name.webdizz.sonar.grammar.sensor.GrammarIssuesSensor;
import name.webdizz.sonar.grammar.spellcheck.GrammarChecker;
import name.webdizz.sonar.grammar.spellcheck.GrammarDictionaryLoader;
import name.webdizz.sonar.grammar.spellcheck.JavaSourceCodeWordFinder;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import java.util.Arrays;
import java.util.List;

@Properties({
        @Property(
                key = PluginParameter.EXCLUSION,
                name = "Exclusion Property",
                description = "Defines resources to be excluded from analysis",
                defaultValue = ""),
        @Property(
                key = PluginParameter.INCLUSION,
                name = "Inclusion Property",
                description = "Defines resources to be included for analysis",
                defaultValue = ""),
        @Property(
                key = PluginParameter.DICTIONARY_PATH,
                name = "Dictionary path",
                description = "Defines resources to be included for analysis",
                defaultValue = "/dict/english.0"),
        @Property(
                key = PluginParameter.ALTERNATIVE_DICTIONARY_PROPERTY_KEY,
                name = "Alternative dictionary",
                description = "Alternative dictionary",
                defaultValue = " ")

})


public class GrammarPlugin extends SonarPlugin {



    @Override
    public List getExtensions() {
        return Arrays
                .asList(// Definitions
                        GrammarRulesDefinition.class,
                        GrammarProfileDefinition.class,
                        // Metrics
                        GrammarMetrics.class,
                        // Sensors
                        GrammarIssuesSensor.class,
                        //Issue review
                        LinkFunction.class,
                        GrammarActionDefinition.class,
                        JavaSourceCodeWordFinder.class,
                        GrammarChecker.class,
                        GrammarDictionaryLoader.class

                );
    }
}
