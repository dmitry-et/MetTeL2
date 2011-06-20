#                                                                                   
# [only works with gnu make]
#

### General ########################################################################
ifeq ($(JAVA_HOME),)
	ifeq ($(JAVAC),) 
	    JAVAC := $(shell which javac)
	endif
	ifeq ($(JAVA),) 
	    JAVA := $(shell which java)
	endif
	ifeq ($(JAVADOC),) 
	    JAVADOC := $(shell which javadoc)
	endif
	ifeq ($(JAR),) 
	    JAR := $(shell which jar)
	endif
else
    JAVA_HOME := /usr/java/jdk
	ifeq ($(JAVAC),) 
	    JAVAC := $(JAVA_HOME)/bin/javac
	endif
	ifeq ($(JAVA),) 
	    JAVA := $(JAVA_HOME)/bin/java
	endif
	ifeq ($(JAVADOC),) 
	    JAVADOC := $(JAVA_HOME)/bin/javadoc
	endif
	ifeq ($(JAR),) 
	    JAR := $(JAVA_HOME)/bin/jar
	endif
endif



# Project name
NAME := mettel
TITLE := MetTeL
CLASS_PREFIX := Mettel

BASE_DIR := $(PWD)

# Log files
JAVAC_LOG_FILE := $(BASE_DIR)/javac.log
TEST_JAVAC_LOG_FILE := $(BASE_DIR)/javac-test.log
TEST_LOG_FILE := $(BASE_DIR)/test.log
TEST_ERR_FILE := $(BASE_DIR)/test.err

# Antlr log files
ANTLR_LEXER_LOG_FILE := $(BASE_DIR)/antlr.lexer.log
ANTLR_PARSER_LOG_FILE := $(BASE_DIR)/antlr.parser.log


# Project paths
BIN_DIR := $(BASE_DIR)/bin
SRC_DIR := $(BASE_DIR)/src
LIB_DIR := $(BASE_DIR)/lib
CLASSES_DIR := $(BASE_DIR)/classes
RESOURCE_DIR := $(BASE_DIR)/rsrc
#AST_CLASSES_DIR := 

# Resources
SRC_RESOURCE_FILES := $(shell find $(RESOURCE_DIR) -type f ! -path '*.svn*') 
RESOURCE_FILES := $(shell echo $(SRC_RESOURCE_FILES) | sed -e 's/rsrc/classes/g')

# Doc paths
DOC_DIR := $(BASE_DIR)/doc
JAVADOC_DIR := $(DOC_DIR)/javadoc

# Javadoc options
JAVADOC_OPTIONS := -private -windowtitle "$(TITLE)" -author -version -use -source 1.5 -verbose

# Class paths
ANTLR:=antlr
ANTLR3:=antlr3
ANTLR3_RUNTIME:=antlr3-runtime
STRINGTEMPLATE:=stringtemplate
ANTLR_JAR:=$(LIB_DIR)/$(ANTLR).jar
ANTLR3_JAR:=$(LIB_DIR)/$(ANTLR3).jar
ANTLR3_RUNTIME_JAR:=$(LIB_DIR)/$(ANTLR3_RUNTIME).jar
STRINGTEMPLATE_JAR:=$(LIB_DIR)/$(STRINGTEMPLATE).jar
COMPILE_CLASSPATH:=$(ANTLR3_RUNTIME_JAR):$(STRINGTEMPLATE_JAR):$(ANTLR_JAR):$(ANTLR3_JAR)

#Antlr shared library
LIBANTLR=$(BIN_DIR)/lib$(ANTLR).so

#Tableau binary
TABLEAU_BIN:=$(BIN_DIR)/$(NAME)-tableau

# Antlr extra options
ANTLR_OPTIONS :=

# Delimiters
DELIM0 :="====================================================================="
DELIM1 :="---------------------------------------------------------------------"

# Jar attributes
JAR_FILE_NAME := $(NAME).jar
JAR_FILE := $(LIB_DIR)/$(NAME).jar
MANIFEST_FILE := $(SRC_DIR)/etc/manifest.mf
PACKAGES_FILE := $(SRC_DIR)/etc/packages
STATISTICS_FILE := $(SRC_DIR)/etc/statistics

### Parser #########################################################################

PARSER_DIR := $(SRC_DIR)/$(NAME)/language

GRAMMAR_NAME := $(CLASS_PREFIX)
LEXER_NAME := $(GRAMMAR_NAME)Lexer
PARSER_NAME := $(GRAMMAR_NAME)Parser
GRAMMAR_FILE_NAME := $(GRAMMAR_NAME).g
GRAMMAR_FILE := $(PARSER_DIR)/grammar/$(GRAMMAR_FILE_NAME)
TOKENS_FILE := $(PARSER_DIR)/$(GRAMMAR_NAME).tokens
PARSER_FILES := $(PARSER_DIR)/$(LEXER_NAME).java $(PARSER_DIR)/$(PARSER_NAME).java 

# Parser doc files
LEXER_DOC_FILE := $(DOC_DIR)/grammar/$(LEXER_NAME).html
PARSER_DOC_FILE := $(DOC_DIR)/grammar/$(PARSER_NAME).html

### Compilation ####################################################################

# Sources
AST_SOURCES := $(shell find $(SRC_DIR)/$(NAME)/language -name '*.java')
PURE_SOURCES := $(shell find $(SRC_DIR) -name '*.java' \
! -name $(LEXER_NAME).java \
! -name $(PARSER_NAME).java \
! -name $(VOCABULARY)TokenTypes.java \
! -name $(PARSER_NAME)TokenTypes.java) 
SOURCES := $(PURE_SOURCES) \
$(PARSER_DIR)/$(LEXER_NAME).java \
$(PARSER_DIR)/$(PARSER_NAME).java

# Classes
#EXTRA_CLASSES := 
CLASSES := $(shell echo $(SOURCES) | sed -e 's/\.java/\.class/g; s/src/classes/g')
#$(EXTRA_CLASSES)

### Test ###########################################################################

RUNTIME_CLASSPATH:=$(ANTLR3_RUNTIME_JAR):$(JAR_FILE)
LOGIC_GENERATION_CLASSPATH:=$(RUNTIME_CLASSPATH)
#$(COMPILE_CLASSPATH):$(JAR_FILE)

TEST_DIR := $(BASE_DIR)/test
TEST_JAR_FILE:=$(LIB_DIR)/test.jar
TEST_COMPILE_CLASSPATH:=$(COMPILE_CLASSPATH):$(LIB_DIR)/junit.jar:$(JAR_FILE)
TEST_CLASSPATH:=$(TEST_COMPILE_CLASSPATH):$(TEST_JAR_FILE)
TEST_CLASSES_DIR:=$(TEST_DIR)/classes
TEST_SRC_DIR:=$(TEST_DIR)/src
TEST_SOURCES := $(shell find $(TEST_SRC_DIR) -name '*.java')
TEST_CLASSES := $(shell echo $(TEST_SOURCES) | sed -e 's/\.java/\.class/g; s/src/classes/g')

TEST_FILE := $(TEST_DIR)/Test
EXAMPLES_DIR := $(BASE_DIR)/examples
TEST_OPTIONS := 
#$(EXAMPLES_DIR)/axiomatic_system.in \
#$(EXAMPLES_DIR)/axiomatic_system.out \
#$(EXAMPLES_DIR)/axiomatic_system.err \
#$(EXAMPLES_DIR)/substitutions.in \
#$(EXAMPLES_DIR)/substitutions.out \
#$(EXAMPLES_DIR)/substitutions.err
JAVA_TEST_OPTIONS := -Xfuture -Xbatch -Xms256M -Xmx400M 
#-Xprof -agentlib:hprof=heap=sites
#-Xnoclassgc

TEST_EXAMPLES_DIR = $(TEST_DIR)/examples
TEST_OUTPUT_DIR = $(TEST_DIR)/output
TEST_LOGIC_DIRS = "ALCO Boolean"
TEST_LOGIC_PARSERS = $(shell find $(TEST_OUTPUT_DIR) -name '*.g')
TEST_LOGIC_SOURCES = $(shell find $(TEST_OUTPUT_DIR) -name '*.java')

DFG_FILE := $(EXAMPLES_DIR)/formulae2dfg.dfg
TO_DFG_FILE := $(EXAMPLES_DIR)/formulae2dfg.in
SPASS_LOG_FILE := $(BASE_DIR)/SPASS.log

### Rules ##########################################################################

.PHONY : $(NAME) jar clear-lexer-log clear-parser-log clear-javac-log clear-jar clear-classes \
clear clean clear-ast-classes compile compile-ast clear-parser-files clear prepare \
clear-log only-lexer only-parser parser lexer-doc parser-doc doc resources test \
all compile-test packages-file java-doc clear-test-log clear-doc clear-test clear-test-jar \
clear-test-classes test-jar old-test junit-test junit SPASS clear-spass-log statistics \
libantlr tableau-bin clear-bin generateLogics generateParsers compileLogics generate

$(NAME): jar

clear-lexer-log:
	@ echo $(DELIM0)
	@ echo "Clearing lexer log file"
	@ echo $(DELIM1)
	@ rm -f -v $(ANTLR_LEXER_LOG_FILE)

clear-parser-log:
	@ echo $(DELIM0)
	@ echo "Clearing parser log file"
	@ echo $(DELIM1)
	@ rm -f -v $(ANTLR_PARSER_LOG_FILE)

clear-javac-log:
	@ echo $(DELIM0)
	@ echo "Clearing javac log file"
	@ echo $(DELIM1)
	@ rm -f -v $(JAVAC_LOG_FILE)

clear-test-log:
	@ echo $(DELIM0)
	@ echo "Clearing test log file"
	@ echo $(DELIM1)
	@ rm -f -v $(TEST_LOG_FILE)
	@ rm -f -v $(TEST_ERR_FILE)
	@ rm -f -v $(TEST_JAVAC_LOG_FILE)

clear-spass-log:
	@ echo $(DELIM0)
	@ echo "Clearing SPASS log file"
	@ echo $(DELIM1)
	@ rm -f -v $(SPASS_LOG_FILE)


clear-log: clear-lexer-log clear-parser-log clear-javac-log clear-test-log clear-spass-log

clear-parser-files: 
	@ echo $(DELIM0)
	@ echo "Clearing parser files"
	@ echo $(DELIM1)
#	@ rm -f -v $(PARSER_DIR)/*.*
	@ rm -f -v $(PARSER_FILES) $(TOKENS_FILE)
    
clear-classes: 
	@ echo $(DELIM0)
	@ echo "Clearing classes"
	@ echo $(DELIM1)
	@ rm -f -r -v $(CLASSES_DIR)
    
clear-jar: 
	@ echo $(DELIM0)
	@ echo "Clearing jar file"
	@ echo $(DELIM1)
	@ rm -f -v $(JAR_FILE)        

clear-bin: 
	@ echo $(DELIM0)
	@ echo "Clearing bin files"
	@ echo $(DELIM1)
	@ rm -f -v $(LIBANTLR) $(TABLEAU_BIN)        


clear: clear-log clear-jar clear-bin clear-classes clear-parser-files clear-doc clear-test
	@ echo $(DELIM0)
	@ echo "Clearing *.bck and *~ files"
	@ echo $(DELIM1)
	@ rm -f -r -v $(shell find $(BASE_DIR)/ -name '*.bck')
	@ rm -f -r -v $(shell find $(BASE_DIR)/ -name '*~')

clear-doc:
	@ echo $(DELIM0)
	@ echo "Clearing documentation files"
	@ echo $(DELIM1)
#	@ rm -f -v -r $(shell find $(JAVADOC_DIR)/* -not -path "*CVS*")
#	@ rm -f -v -r $(shell find $(DOC_DIR)/grammar/* -not -path "*CVS*")
	@ rm -f -v -r $(JAVADOC_DIR)
	@ rm -f -v -r $(DOC_DIR)/grammar

clear-test: clear-test-log clear-test-jar clear-test-classes

clear-test-jar: 
	@ echo $(DELIM0)
	@ echo "Clearing test jar file"
	@ echo $(DELIM1)
	@ rm -f -v $(TEST_JAR_FILE)
	
clear-test-classes:
	@ echo $(DELIM0)
	@ echo "Clearing test classes"
	@ echo $(DELIM1)
	@ rm -f -r -v $(TEST_DIR)/*.class  
	@ rm -f -r -v $(TEST_CLASSES_DIR)
       
prepare: clear $(CLASSES_DIR)

$(CLASSES_DIR): 
	@ echo $(DELIM0)
	@ echo "Preparing"
	@ echo $(DELIM1)
	@ mkdir $(CLASSES_DIR)
	
$(TEST_CLASSES_DIR): 
	@ echo $(DELIM0)
	@ echo "Preparing test"
	@ echo $(DELIM1)
	@ mkdir $(TEST_CLASSES_DIR)

$(PARSER_FILES): $(GRAMMAR_FILE)
	@ echo $(DELIM0)
	@ echo "Making parser and parser documentation"
	@ echo $(DELIM1)
	@ mkdir -p $(DOC_DIR)/grammar
	@ cd $(PARSER_DIR)/grammar && $(JAVA) -classpath $(COMPILE_CLASSPATH) org.antlr.Tool -o $(PARSER_DIR) -print $(ANTLR_OPTIONS) $(GRAMMAR_FILE_NAME) 1>$(DOC_DIR)/grammar/$(PARSER_NAME).txt 2>$(ANTLR_PARSER_LOG_FILE) && rm -f $(TOKENS_FILE) 
	@ cat $(ANTLR_PARSER_LOG_FILE)
	@ cd $(BASE_DIR)

only-parser: $(PARSER_FILES)
       
parser: clear-parser-files only-parser

parser-doc: only-parser

packages-file:
	@ cd $(SRC_DIR) && \
		(find * -not -path "etc*" -not -path "*CVS*" -not -path "*svn*" -not -path "*/parser/grammar" -type d -print | sed -e 's/\//./g') >$(PACKAGES_FILE)
	@ cd $(BASE_DIR)
	

statistics:
	@ wc -l -c $(PURE_SOURCES) $(PARSER_FILE) $(LEXER_FILE) > $(STATISTICS_FILE)
	@ cat $(STATISTICS_FILE)

java-doc: packages-file
	@ echo $(DELIM0)
	@ echo "Making java documentation"
	@ echo $(DELIM1)
	@ mkdir -p $(JAVADOC_DIR)
	@ $(JAVADOC) $(JAVADOC_OPTIONS) -classpath $(COMPILE_CLASSPATH) -sourcepath $(SRC_DIR) -d $(JAVADOC_DIR) @$(PACKAGES_FILE)

doc: lexer-doc parser-doc java-doc
    
clear-ast-classes:
	@ rm -f $(AST_CLASSES_DIR)/*.class

compile-ast: clear-ast-classes $(AST_SOURCES) $(CLASSES_DIR)
	@ echo $(DELIM0)
	@ echo "Compiling AST"
	@ echo $(DELIM1)
	@ $(JAVAC) -classpath $(COMPILE_CLASSPATH) -d "$(CLASSES_DIR)" $(AST_SOURCES) 2>$(JAVAC_LOG_FILE) || (cat $(JAVAC_LOG_FILE) && exit 1)

compile: $(CLASSES) 

$(CLASSES): $(CLASSES_DIR) $(SOURCES)
#	@ echo $(CLASSES)
	@ echo $(DELIM0)
	@ echo "Compiling project"
	@ echo $(DELIM1)
	@ $(JAVAC) -Xlint:unchecked -classpath $(COMPILE_CLASSPATH) -d "$(CLASSES_DIR)" $(SOURCES) 2>$(JAVAC_LOG_FILE) || (cat $(JAVAC_LOG_FILE) && exit 1)
    
resources: $(RESOURCE_FILES)

$(RESOURCE_FILES): $(CLASSES_DIR) $(SRC_RESOURCE_FILES)
	@ echo $(DELIM0)
	@ echo "Copying resources"
	@ echo $(DELIM1)
	@ cd $(RESOURCE_DIR) && cp -R --parents $(shell cd $(RESOURCE_DIR) && find . -type f ! -path '*.svn*') $(CLASSES_DIR)
	
#	echo $(RESOURCE_FILES)
#	echo $(SRC_RESOURCE_FILES)
	
#/ && cp -v -R --parents $(shell find . -type f ! -name '*.svn*') $(CLASSES_DIR)
#@ cd $(BASE_DIR) 
	
#	@ for i in $(SRC_RC_FILES); do \
#	    j=$$(echo $$i | sed -e 's/src/classes/g'); \
#	    mkdir -p $$(dirname $$j); \
#	    cp -r $$i $$j; done

jar: $(JAR_FILE)

$(JAR_FILE): $(CLASSES) $(RESOURCE_FILES) $(MANIFEST_FILE)
	@ echo $(DELIM0)
	@ echo "Building runtime jar ($(JAR_FILE_NAME))"
	@ echo $(DELIM1)
	@ cd $(CLASSES_DIR) && $(JAR) cvfm $(JAR_FILE) $(MANIFEST_FILE) *
	@ cd $(BASE_DIR)

generateLogics: $(JAR_FILE) $(TEST_CLASSES_DIR)
	@ echo $(DELIM0)
	@ echo "Generating logics"
	@ echo $(DELIM1)
	@ for D in "$(TEST_LOGIC_DIRS)"; do echo "Generating $${D}"; java -cp $(RUNTIME_CLASSPATH) mettel.MettelGenerator -i "$(TEST_EXAMPLES_DIR)/$${D}/$${D}.s" -d "test/output"; done
	
generateParsers: generateLogics
#	@ TEST_LOGIC_PARSERS = $(shell find $(TEST_OUTPUT_DIR) -name '*.g')
#	@ TEST_LOGIC_SOURCES = $(shell find $(TEST_OUTPUT_DIR) -name '*.java')
	@ echo $(DELIM0)
	@ echo "Generating parsers for logics"
	@ echo $(DELIM1)
	@ for P in $(shell find $(TEST_OUTPUT_DIR) -name '*.g'); do java -cp $(COMPILE_CLASSPATH) org.antlr.Tool "$${P}"; done
	
compileLogics: generateParsers
	@ echo $(DELIM0)
	@ echo "Compiling sources for logics"
	@ echo $(DELIM1)
	@ $(JAVAC) -classpath $(LOGIC_GENERATION_CLASSPATH):$(LIB_DIR)/junit.jar -d $(TEST_CLASSES_DIR) $(shell find $(TEST_OUTPUT_DIR) -name '*.java')

generate: compileLogics

$(TEST_CLASSES): $(TEST_SOURCES) $(JAR_FILE) $(TEST_CLASSES_DIR)
	@ echo $(DELIM0)
	@ echo "Compiling test"
	@ echo $(DELIM1)
	@ $(JAVAC) -classpath $(TEST_COMPILE_CLASSPATH) -d "$(TEST_CLASSES_DIR)" $(TEST_SOURCES) 2>$(TEST_JAVAC_LOG_FILE) || (cat $(TEST_JAVAC_LOG_FILE) && exit 1)
    
#$(TEST_FILE).class: $(TEST_FILE).java $(JAR_FILE)
#	@ echo $(DELIM0)
#	@ echo "Compiling test"
#	@ echo $(DELIM1)
#	@ $(JAVAC) -classpath $(RUNTIME_CLASSPATH) -d "$(TEST_DIR)" $(TEST_FILE).java 2>$(TEST_LOG_FILE) || (cat $(TEST_LOG_FILE) && exit 1)

#compile-test: $(TEST_FILE).class

compile-test: $(TEST_CLASSES)

$(TEST_JAR_FILE): $(TEST_CLASSES) generate
	@ echo $(DELIM0)
	@ echo "Building runtime jar (test.jar)"
	@ echo $(DELIM1)
	@ cd $(TEST_CLASSES_DIR) && $(JAR) cvf $(TEST_JAR_FILE) *
	@ cd $(BASE_DIR)

test-jar: $(TEST_JAR_FILE)

define allTests
$(JAVA) -classpath .:$(TEST_CLASSPATH) $(JAVA_TEST_OPTIONS)	mettel.AllTests 2>$(TEST_ERR_FILE) || (cat $(TEST_ERR_FILE) && exit 1)
endef

test: $(TEST_JAR_FILE)
	@ echo $(DELIM0)
	@ echo "Testing"
	@ echo $(DELIM1)
	@ $(allTests)
#2>$(TEST_LOG_FILE) || (cat $(TEST_LOG_FILE) && exit 1)

junit-test: test

junit: test 

#parser-test:$(JAR_FILE)
#	@ echo $(DELIM0)
#	@ echo "Testing parser"
#	@ echo $(DELIM1)
#	@ $(JAVA_HOME)/bin/java -classpath .:$(RUNTIME_CLASSPATH) $(JAVA_TEST_OPTIONS) mettel.parser.MettelParser || cat $(BASE_DIR)/examples/formulae.err
##
    
old-test: $(TEST_JAR_FILE) 
	@ echo $(DELIM0)
	@ echo "Testing"
	@ echo $(DELIM1)
	@ $(JAVA) -classpath $(TEST_CLASSPATH) $(JAVA_TEST_OPTIONS) Test $(TEST_OPTIONS)
#	@ cd $(BASE_DIR)
#	@ $(JAVA_HOME)/bin/java -classpath $(RUNTIME_CLASSPATH) bdl.language.parser.BDLParser

$(DFG_FILE): $(TO_DFG_FILE) $(TEST_JAR_FILE)
	@ $(allTests)

SPASS:	$(DFG_FILE)
	@ echo $(DELIM0)
	@ echo "SPASS"
	@ echo $(DELIM1)
	@ SPASS $(DFG_FILE)
#1>SPASS.log 2>&1 && cat SPASS.log

clean : clear

all: clean jar doc test tableau-bin

$(LIBANTLR): $(ANTLR_JAR)
	@ echo $(DELIM0)
	@ echo "Making $(LIBANTLR)"
	@ echo $(DELIM1)
	@ gcj -shared -fPIC -o $(LIBANTLR) $(ANTLR_JAR) && strip $(LIBANTLR)
	
libantlr: $(LIBANTLR)

$(TABLEAU_BIN): $(LIBANTLR) $(SOURCES)
	@ echo $(DELIM0)
	@ echo "Making $(TITLE) tableau executables"
	@ echo $(DELIM1)
	@ gcj -L$(BIN_DIR) -l$(ANTLR) --main=mettel.tableau.wrapper.MettelTableauWrapper -o $(TABLEAU_BIN) --classpath $(COMPILE_CLASSPATH) $(SOURCES)
	
tableau-bin: $(TABLEAU_BIN)
	 


	