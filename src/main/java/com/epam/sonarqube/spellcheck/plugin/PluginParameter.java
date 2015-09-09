package com.epam.sonarqube.spellcheck.plugin;

import org.sonar.api.CoreProperties;

/**
 * The pool of constants used in the grammar-plugin
 */
public interface PluginParameter {
    /**
     * The name of sonar-plugin
     */
    String PLUGIN_NAME = "Grammar";

    /**
     * The name of repository for grammar rules
     */
    String REPOSITORY_NAME = PLUGIN_NAME + " Repository";

    /**
     * The name of grammar profile
     */
    String PROFILE_NAME = PLUGIN_NAME + " Profile";

    /**
     * The language of grammar profile
     */
    String PROFILE_LANGUAGE = CoreProperties.CATEGORY_JAVA;

    /**
     * The key of grammar-rules-repository
     */
    String REPOSITORY_KEY = "snrgrm";

    /**
     * The name of grammar-rule
     */
    String SONAR_GRAMMAR_RULE_KEY = "sonar_grammar_rule";

    /**
     * The name of grammar-rule
     */
    String SONAR_GRAMMAR_RULE_NAME = "Grammar Rule";

    /**
     * The description of grammar-rule
     */
    String SONAR_GRAMMAR_RULE_DESCRIPTION = "Analyses source code for english grammar issues.";

    /**
     * Error description
     */
    String ERROR_DESCRIPTION = "Invalid word is: \'";

    /**
     * Action name for adding to dictionary
     */
    String ADD_TO_DICT = "add-to-dict";

    /**
     * Add  to dictionary link caption
     */
    String SONAR_GRAMMAR_ISSUE_DATA_PROPERTY_KEY = "grammar-issue-key";

    /**
     * Separator for store alternate dictionary
     */
    String SEPARATOR_CHAR = ",";

    /**
     * Parameters for set cost of spelling
     */

    /**
     * exclusion word list
     **/
    String EXCLUSION = "sonarqube-spellcheck.dictionary.exclusion";

    /**
     * list of words to be included to main dictionary
     **/
    String INCLUSION = "sonarqube-spellcheck.dictionary.inclusion";

    /**
     * path to main dictionary
     **/
    String DICTIONARY_PATH = "sonarqube-spellcheck.dictionary.path";

    /**
     * manual dictionary name
     */
    String ALTERNATIVE_DICTIONARY_PROPERTY_KEY = "sonarqube-spellcheck.alternative.dictionary";

    /**
     * the maximum cost of suggested spelling. Any suggestions that cost more are thrown away
     * integer greater than 1)
     */
    String SPELL_THRESHOLD = "sonarqube-spellcheck.spell.threshold.value";

    /**
     * minimum word length to be analyzed
     */
    String SPELL_MINIMUMWORDLENGTH = "sonarqube-spellcheck.spell.minimum.word.length";

    /**
     * words that are all upper case are not spell checked, example: "CIA" <br/>(boolean)
     */
    String SPELL_IGNOREUPPERCASE = "sonarqube-spellcheck.spell.ignore.upper.case";

    /**
     * words that have mixed case are not spell checked, example: "SpellChecker"<br/>(boolean)\
     */
    String SPELL_IGNOREMIXEDCASE = "sonarqube-spellcheck.spell.ignore.mixed.case";

    /**
     * words that look like an Internet address are not spell checked, example: "http://www.google.com" <br/>(boolean)
     */
    String SPELL_IGNOREINTERNETADDRESSES = "sonarqube-spellcheck.spell.ignore.internet.address";

    /**
     * words that have digits in them are not spell checked, example: "mach5" <br/>(boolean)
     */
    String SPELL_IGNOREDIGITWORDS = "sonarqube-spellcheck.spell.ignore.digit.words";

}
