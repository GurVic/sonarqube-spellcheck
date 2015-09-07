package com.epam.sonarqube.grammar.plugin;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.PropertyType;
import org.sonar.api.SonarPlugin;
import org.sonar.api.config.PropertyDefinition;

import com.epam.sonarqube.grammar.plugin.issue.tracking.GrammarActionDefinition;
import com.epam.sonarqube.grammar.plugin.issue.tracking.LinkFunction;
import com.epam.sonarqube.grammar.plugin.profile.GrammarProfileDefinition;
import com.epam.sonarqube.grammar.plugin.rule.GrammarRulesDefinition;
import com.epam.sonarqube.grammar.plugin.sensor.GrammarIssuesSensor;
import com.epam.sonarqube.grammar.plugin.spellcheck.GrammarChecker;
import com.epam.sonarqube.grammar.plugin.spellcheck.GrammarDictionaryLoader;
import com.epam.sonarqube.grammar.plugin.spellcheck.JavaSourceCodeWordFinder;
import com.epam.sonarqube.grammar.plugin.spellcheck.SpellCheckerFactory;
import com.epam.sonarqube.grammar.plugin.utils.SpellCheckerUtil;


public class GrammarPlugin extends SonarPlugin {

    private static final String SPELL_CHECKER_CATEGORY = "Spell Checker";
    private static final String DICTIONARY_CATEGORY = "Dictionary";

    @Override
    public List getExtensions() {
        return Arrays
                .asList(// Definitions
                        GrammarRulesDefinition.class,
                        GrammarActionDefinition.class,
                        GrammarProfileDefinition.class,
                        // Metrics
                        GrammarMetrics.class,
                        // Sensors
                        GrammarIssuesSensor.class,
                        //Issue review
                        LinkFunction.class,
                        //Instantiated by IoC as used injection
                        JavaSourceCodeWordFinder.class,
                        GrammarChecker.class,
                        GrammarDictionaryLoader.class,
                        SpellCheckerFactory.class,
                        SpellCheckerUtil.class,

                        PropertyDefinition.builder(PluginParameter.SPELL_MINIMUMWORDLENGTH).name("Minimum word length")
                                .type(PropertyType.INTEGER).description("Defines minimum word length to analyse")
                                .defaultValue("3").subCategory(SPELL_CHECKER_CATEGORY).build(),

                        PropertyDefinition.builder(PluginParameter.SPELL_IGNOREMIXEDCASE).name("Ignore mixed case")
                                .type(PropertyType.BOOLEAN).description("Words that have mixed case are not spell checked, example: 'SpellChecker'").defaultValue("false").subCategory(SPELL_CHECKER_CATEGORY)
                                .build(),

                        PropertyDefinition.builder(PluginParameter.SPELL_IGNOREUPPERCASE).name("Ignore uppercase")
                                .type(PropertyType.BOOLEAN).description("Words that are all upper case are not spell checked, example: 'CIA'").defaultValue("true").subCategory(SPELL_CHECKER_CATEGORY).build(),

                        PropertyDefinition.builder(PluginParameter.SPELL_IGNOREDIGITWORDS)
                                .name("Ignore words with digits").type(PropertyType.BOOLEAN)
                                .description("Words that have digits in them are not spell checked, example: 'mach5'")
                                .defaultValue("false").subCategory(SPELL_CHECKER_CATEGORY).build(),

                        PropertyDefinition.builder(PluginParameter.SPELL_IGNOREINTERNETADDRESSES)
                                .name("Ignore words like internet address").type(PropertyType.BOOLEAN)
                                .description("Words that look like an Internet address are not spell checked, example: 'http://www.google.com'").defaultValue("true").subCategory(SPELL_CHECKER_CATEGORY)
                                .build(),
                        PropertyDefinition.builder(PluginParameter.SPELL_THRESHOLD).name("Threshold value")
                                .type(PropertyType.INTEGER).description("The maximum cost of suggested spelling. Any suggestions that cost more are thrown away").defaultValue("1")
                                .subCategory(SPELL_CHECKER_CATEGORY).build(),

                        PropertyDefinition.builder(PluginParameter.EXCLUSION).name("Exclusions")
                                .type(PropertyType.STRING).description("Defines resources to be excluded from analysis")
                                .subCategory(DICTIONARY_CATEGORY).defaultValue("").build(),

                        PropertyDefinition.builder(PluginParameter.INCLUSION).name("Inclusions")
                                .type(PropertyType.STRING).description("Defines resources to be included for analysis")
                                .subCategory(DICTIONARY_CATEGORY).defaultValue("").build(),

                        PropertyDefinition.builder(PluginParameter.DICTIONARY_PATH).name("Default dictionary path")
                                .type(PropertyType.STRING).description("Defines default dictionary path")
                                .defaultValue("/dict/english.0").subCategory(DICTIONARY_CATEGORY).build(),

                        PropertyDefinition.builder(PluginParameter.ALTERNATIVE_DICTIONARY_PROPERTY_KEY)
                                .name("Alternative dictionary").type(PropertyType.STRING).description("The list of comma separated words to compile alternative  dictionary specific to your installation").type(PropertyType.TEXT).defaultValue("").subCategory(DICTIONARY_CATEGORY).build()
                );
    }
}
