package name.webdizz.sonar.grammar.sensor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import name.webdizz.sonar.grammar.GrammarPlugin;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;
import name.webdizz.sonar.grammar.PluginParameter;
import name.webdizz.sonar.grammar.spellcheck.GrammarChecker;

/**
 * The sensor for project files
 */
public class GrammarIssuesSensor implements Sensor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrammarIssuesSensor.class);
    private final FileSystem fileSystem;
    private final ResourcePerspectives perspectives;
    private final GrammarChecker grammarChecker;
    private final Lock wrapperLock = new ReentrantLock();
    private GrammarIssuesWrapper templateWrapper;


    public GrammarIssuesSensor(FileSystem fileSystem, ResourcePerspectives perspectives, final GrammarChecker grammarChecker) {
        this.fileSystem = fileSystem;
        this.perspectives = perspectives;
        this.grammarChecker = grammarChecker;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Created the bean of grammar sensor.");
        }
    }

    @Override
    public void analyse(Project module, SensorContext context) {
        if (LOGGER.isDebugEnabled()) {
            logWhenAnalyse(module, context);
        }
        grammarChecker.initialize();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Checking {}-files.", PluginParameter.PROFILE_LANGUAGE);
        }
        FilePredicate languagePredicate = fileSystem.predicates().hasLanguage(PluginParameter.PROFILE_LANGUAGE);
        for (final InputFile inputFile : fileSystem.inputFiles(languagePredicate)) {
            processInputFile(inputFile);
        }
    }

    @Override
    public boolean shouldExecuteOnProject(Project project) {
        // This sensor is executed only when there are Java files
        return fileSystem.hasFiles(fileSystem.predicates().hasLanguage(PluginParameter.PROFILE_LANGUAGE));
    }

    private void logWhenAnalyse(final Project module, final SensorContext context) {
        Object[] arguments = new Object[]{module.getName(), module.getKey(), module.getDescription()};
        LOGGER.debug("Module name={} key={} description=\"{}\"", arguments);
        LOGGER.debug("SensorContext {}", context);
        LOGGER.debug("Initialize the GrammarChecker.");
    }

    private void processInputFile(final InputFile inputFile) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Processing input-file {}", inputFile);
        }
        int lineCounter = 1;
        try {
            final List<String> code = FileUtils.readLines(inputFile.file());

            for (final String line : code) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Has read code-lines from {}\nHave read {} lines.", inputFile, code.size());
                    LOGGER.debug("Processing {}:\"{}\"", lineCounter, line);
                }
                processInputCodeCurrentLine(line, inputFile, lineCounter);
                lineCounter++;
            }
        } catch (IOException ex) {
            LOGGER.error("Can't read data from file " + inputFile.absolutePath(), ex);
        }
    }

    private void processInputCodeCurrentLine(final String line, final InputFile inputFile, int lineNumber) {
        if (!StringUtils.isEmpty(line)) {
            final GrammarIssuesWrapper lineWrapper = createWrapper(inputFile, line, lineNumber);

            if (LOGGER.isDebugEnabled()) {
                final Object[] arguments = new Object[]{lineNumber, line, inputFile};
                LOGGER.debug("Prepared issues-wrapper for \n {}:\"{}\"\nin{}", arguments);
                LOGGER.debug(lineWrapper.toString());
                LOGGER.debug("Begin check line \"{}\"", line);
            }
            grammarChecker.checkSpelling(line, new GrammarViolationTrigger(lineWrapper));

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("End check line \"{}\"", line);
            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Skipped empty line #{} in {}", lineNumber, inputFile);
            }
        }
    }

    private GrammarIssuesWrapper createWrapper(final InputFile resource, final String line, final int lineNumber) {
        wrapperLock.lock();
        try {
            return (templateWrapper == null)
                    ? getGrammarIssuesWrapperWhenNull(resource, line, lineNumber)
                    : getGrammarIssuesWrapper(resource, line, lineNumber);

        } finally {
            wrapperLock.unlock();
        }
    }

    private GrammarIssuesWrapper getGrammarIssuesWrapperWhenNull(final InputFile resource, final String line, final int lineNumber) {
        return templateWrapper = GrammarIssuesWrapper.builder()
                .setInputFile(resource)
                .setLine(line)
                .setLineNumber(lineNumber)
                .setPerspectives(perspectives)
                .setRuleKey(RuleKey.of(PluginParameter.REPOSITORY_KEY, PluginParameter.SONAR_GRAMMAR_RULE_KEY))
                .build();
    }

    private GrammarIssuesWrapper getGrammarIssuesWrapper(final InputFile resource, final String line, final int lineNumber) {
        return GrammarIssuesWrapper.builder(templateWrapper)
                .setInputFile(resource)
                .setLine(line)
                .setLineNumber(lineNumber)
                .build();
    }

    @Override
    public String toString() {
        return "Grammar Issues";
    }

}
