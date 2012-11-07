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
TITLE := MetTeL2
CLASS_PREFIX := Mettel

BASE_DIR := $(PWD)

# Log files
JAVAC_LOG_FILE := javac.log
TEST_JAVAC_LOG_FILE := javac-test.log
TEST_LOG_FILE := test.log
TEST_ERR_FILE := test.err

# Project paths
BIN_DIR := bin
SRC_DIR := src
LIB_DIR := lib
CLASSES_DIR := classes
RESOURCE_DIR := rsrc
#AST_CLASSES_DIR := 

# Resources
SRC_RESOURCE_FILES := $(shell find $(RESOURCE_DIR) -type f ! -path '*.svn*') 
RESOURCE_FILES := $(shell echo $(SRC_RESOURCE_FILES) | sed -e 's/rsrc/classes/g')

# Doc paths
DOC_DIR := doc
JAVADOC_DIR := $(DOC_DIR)/javadoc

# Javadoc options
JAVADOC_OPTIONS := -private -windowtitle "$(TITLE)" -author -version -use -source 1.6 -verbose

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
JAR_FILE_NAME := $(NAME)2.jar
JAR_FILE := $(LIB_DIR)/$(NAME)2.jar
MANIFEST_FILE := $(SRC_DIR)/etc/manifest.mf
PACKAGES_FILE := $(SRC_DIR)/etc/packages
STATISTICS_FILE := $(SRC_DIR)/etc/statistics

CORE_PACKAGE_SUBDIR = mettel/core
CORE_JAR_FILE_NAME := $(NAME)2-core.jar
CORE_JAR_FILE := $(LIB_DIR)/$(CORE_JAR_FILE_NAME)
CORE_MANIFEST_FILE := $(SRC_DIR)/etc/core-manifest.mf

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

# Antlr log files
ANTLR_LEXER_LOG_FILE := antlr.lexer.log
ANTLR_PARSER_LOG_FILE := antlr.parser.log

### FO-Parser #########################################################################

FO_PARSER_DIR := $(SRC_DIR)/$(NAME)/fo

FO_GRAMMAR_NAME := $(CLASS_PREFIX)FO
FO_LEXER_NAME := $(FO_GRAMMAR_NAME)Lexer
FO_PARSER_NAME := $(FO_GRAMMAR_NAME)Parser
FO_GRAMMAR_FILE_NAME := $(FO_GRAMMAR_NAME).g
FO_GRAMMAR_FILE := $(FO_PARSER_DIR)/grammar/$(FO_GRAMMAR_FILE_NAME)
FO_TOKENS_FILE := $(FO_PARSER_DIR)/$(FO_GRAMMAR_NAME).tokens
FO_PARSER_FILES := $(FO_PARSER_DIR)/$(FO_LEXER_NAME).java $(FO_PARSER_DIR)/$(FO_PARSER_NAME).java 

# FO-Parser doc files
FO_LEXER_DOC_FILE := $(DOC_DIR)/grammar/$(FO_LEXER_NAME).html
FO_PARSER_DOC_FILE := $(DOC_DIR)/grammar/$(FO_PARSER_NAME).html

# Antlr log files
ANTLR_FO_LEXER_LOG_FILE := antlr.fo.lexer.log
ANTLR_FO_PARSER_LOG_FILE := antlr.fo.parser.log

### Compilation ####################################################################

# Sources
AST_SOURCES := $(shell find $(SRC_DIR)/$(NAME)/language -name '*.java')
PURE_SOURCES := $(shell find $(SRC_DIR) -name '*.java' \
! -path "*/$(CORE_PACKAGE_SUBDIR)/*" \
! -name $(LEXER_NAME).java \
! -name $(PARSER_NAME).java \
! -name $(FO_LEXER_NAME).java \
! -name $(FO_PARSER_NAME).java \
! -name $(VOCABULARY)TokenTypes.java \
! -name $(PARSER_NAME)TokenTypes.java) 
SOURCES := $(PURE_SOURCES) \
$(PARSER_DIR)/$(LEXER_NAME).java \
$(PARSER_DIR)/$(PARSER_NAME).java \
$(FO_PARSER_DIR)/$(FO_LEXER_NAME).java \
$(FO_PARSER_DIR)/$(FO_PARSER_NAME).java

CORE_SOURCES := $(shell find $(SRC_DIR)/$(CORE_PACKAGE_SUBDIR) -name '*.java')

# Classes
#EXTRA_CLASSES := 
CLASSES := $(shell echo $(SOURCES) | sed -e 's/\.java/\.class/g; s/src/classes/g')
#$(EXTRA_CLASSES)
CORE_CLASSES := $(shell echo $(CORE_SOURCES) | sed -e 's/\.java/\.class/g; s/src/classes/g')


### Test ###########################################################################

RUNTIME_CLASSPATH:=$(ANTLR3_JAR):$(ANTLR_JAR):$(STRINGTEMPLATE_JAR):$(JAR_FILE):$(CORE_JAR_FILE)
LOGIC_GENERATION_CLASSPATH:=$(RUNTIME_CLASSPATH)
#$(COMPILE_CLASSPATH):$(JAR_FILE)

TEST_DIR := test
TEST_JAR_FILE:=$(LIB_DIR)/test.jar
TEST_COMPILE_CLASSPATH:=$(COMPILE_CLASSPATH):$(LIB_DIR)/junit.jar:$(JAR_FILE):$(CORE_JAR_FILE)
TEST_CLASSPATH:=$(TEST_COMPILE_CLASSPATH):$(TEST_JAR_FILE)
TEST_CLASSES_DIR:=$(TEST_DIR)/classes
TEST_SRC_DIR:=$(TEST_DIR)/src
TEST_SOURCES := $(shell find $(TEST_SRC_DIR) -name '*.java')
TEST_CLASSES := $(shell echo $(TEST_SOURCES) | sed -e 's/\.java/\.class/g; s/src/classes/g')

TEST_FILE := $(TEST_DIR)/Test
EXAMPLES_DIR := examples
TEST_OPTIONS := 
#$(EXAMPLES_DIR)/axiomatic_system.in \
#$(EXAMPLES_DIR)/axiomatic_system.out \
#$(EXAMPLES_DIR)/axiomatic_system.err \
#$(EXAMPLES_DIR)/substitutions.in \
#$(EXAMPLES_DIR)/substitutions.out \
#$(EXAMPLES_DIR)/substitutions.err
JAVA_TEST_OPTIONS := -Xfuture -Xbatch -Xms256M -Xmx1024M 
#-Xprof -agentlib:hprof=heap=sites
#-Xnoclassgc

TEST_EXAMPLES_DIR = $(TEST_DIR)/examples
TEST_OUTPUT_DIR = $(TEST_DIR)/output
TEST_LOGIC_DIRS = "bool ALCO S4 ALBOid LTL LTLC lists KE3 fotheory IEL"
TEST_LOGIC_PARSERS = $(shell find $(TEST_OUTPUT_DIR) -name '*.g')
TEST_LOGIC_SOURCES = $(shell find $(TEST_OUTPUT_DIR) -name '*.java')

DFG_FILE := $(EXAMPLES_DIR)/formulae2dfg.dfg
TO_DFG_FILE := $(EXAMPLES_DIR)/formulae2dfg.in
SPASS_LOG_FILE := SPASS.log

### Rules ##########################################################################

.PHONY : $(NAME) jar clear-lexer-log clear-parser-log clear-fo-lexer-log clear-fo-parser-log \
clear-javac-log clear-jar clear-classes \
clear clean clear-ast-classes compile compile-ast clear-parser-files clear-fo-parser-files clear prepare \
clear-log only-lexer only-parser parser only-fo-parser fo-parser lexer-doc parser-doc parser-fo-doc doc resources test \
all compile-test packages-file java-doc clear-test-log clear-doc clear-test clear-test-jar clear-test-output\
clear-test-classes test-jar old-test junit-test junit SPASS clear-spass-log statistics \
libantlr tableau-bin clear-bin generateLogics generateParsers compileLogics generate core-jar
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

clear-fo-lexer-log:
	@ echo $(DELIM0)
	@ echo "Clearing first-order language lexer log file"
	@ echo $(DELIM1)
	@ rm -f -v $(ANTLR_FO_LEXER_LOG_FILE)

clear-fo-parser-log:
	@ echo $(DELIM0)
	@ echo "Clearing first-order language parser log file"
	@ echo $(DELIM1)
	@ rm -f -v $(ANTLR_FO_PARSER_LOG_FILE)

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


clear-log: clear-lexer-log clear-parser-log clear-fo-lexer-log clear-fo-parser-log clear-javac-log clear-test-log clear-spass-log

clear-parser-files: 
	@ echo $(DELIM0)
	@ echo "Clearing parser files"
	@ echo $(DELIM1)
#	@ rm -f -v $(PARSER_DIR)/*.*
	@ rm -f -v $(PARSER_FILES) $(TOKENS_FILE)

clear-fo-parser-files: 
	@ echo $(DELIM0)
	@ echo "Clearing first-order language parser files"
	@ echo $(DELIM1)
#	@ rm -f -v $(PARSER_DIR)/*.*
	@ rm -f -v $(FO_PARSER_FILES) $(FO_TOKENS_FILE)

    
clear-classes: 
	@ echo $(DELIM0)
	@ echo "Clearing classes"
	@ echo $(DELIM1)
	@ rm -f -r -v $(CLASSES_DIR)
    
clear-jar: 
	@ echo $(DELIM0)
	@ echo "Clearing jar file"
	@ echo $(DELIM1)
	@ rm -f -v $(JAR_FILE) $(CORE_JAR_FILE)      

clear-bin: 
	@ echo $(DELIM0)
	@ echo "Clearing bin files"
	@ echo $(DELIM1)
	@ rm -f -v $(LIBANTLR) $(TABLEAU_BIN)        


clear: clear-log clear-jar clear-bin clear-classes clear-parser-files clear-fo-parser-files clear-doc clear-test
	@ echo $(DELIM0)
	@ echo "Clearing *.bck and *~ files"
	@ echo $(DELIM1)
	@ rm -f -r -v $(shell find . -name '*.bck')
	@ rm -f -r -v $(shell find . -name '*~')

clear-doc:
	@ echo $(DELIM0)
	@ echo "Clearing documentation files"
	@ echo $(DELIM1)
#	@ rm -f -v -r $(shell find $(JAVADOC_DIR)/* -not -path "*CVS*")
#	@ rm -f -v -r $(shell find $(DOC_DIR)/grammar/* -not -path "*CVS*")
	@ rm -f -v -r $(JAVADOC_DIR)
	@ rm -f -v -r $(DOC_DIR)/grammar

clear-test: clear-test-log clear-test-jar clear-test-classes clear-test-output

clear-test-output:
	@ echo $(DELIM0)
	@ echo "Clearing test output directory"
	@ echo $(DELIM1)
	@ rm -f -r -v $(TEST_OUTPUT_DIR)

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
	@ $(JAVA) -classpath $(COMPILE_CLASSPATH) org.antlr.Tool -fo $(PARSER_DIR) -print $(ANTLR_OPTIONS) $(GRAMMAR_FILE) 1>$(DOC_DIR)/grammar/$(PARSER_NAME).txt 2>$(ANTLR_PARSER_LOG_FILE) && rm -f $(TOKENS_FILE) 
	@ cat $(ANTLR_PARSER_LOG_FILE)

only-parser: $(PARSER_FILES)
       
parser: clear-parser-files only-parser

parser-doc: only-parser

$(FO_PARSER_FILES): $(FO_GRAMMAR_FILE)
	@ echo $(DELIM0)
	@ echo "Making first-order language parser and parser documentation"
	@ echo $(DELIM1)
	@ mkdir -p $(DOC_DIR)/grammar
	@ $(JAVA) -classpath $(COMPILE_CLASSPATH) org.antlr.Tool -fo $(FO_PARSER_DIR) -print $(ANTLR_OPTIONS) $(FO_GRAMMAR_FILE) 1>$(DOC_DIR)/grammar/$(FO_PARSER_NAME).txt 2>$(ANTLR_FO_PARSER_LOG_FILE) && rm -f $(FO_TOKENS_FILE) 
	@ cat $(ANTLR_FO_PARSER_LOG_FILE)

only-fo-parser: $(FO_PARSER_FILES)
       
fo-parser: clear-fo-parser-files only-fo-parser

fo-parser-doc: only-fo-parser

packages-file:
	@ cd $(SRC_DIR) && \
		(find * -not -path "etc*" -not -path "*CVS*" -not -path "*svn*" -not -path "*/parser/grammar" -type d -print | sed -e 's/\//./g') >$(PACKAGES_FILE)
	@ cd $(BASE_DIR)
	

statistics:
	@ wc -l -c $(PURE_SOURCES) $(PARSER_FILE) $(LEXER_FILE) $(FO_PARSER_FILE) $(FO_LEXER_FILE) > $(STATISTICS_FILE)
	@ cat $(STATISTICS_FILE)

java-doc: packages-file
	@ echo $(DELIM0)
	@ echo "Making java documentation"
	@ echo $(DELIM1)
	@ mkdir -p $(JAVADOC_DIR)
	@ $(JAVADOC) $(JAVADOC_OPTIONS) -classpath $(COMPILE_CLASSPATH) -sourcepath $(SRC_DIR) -d $(JAVADOC_DIR) @$(PACKAGES_FILE)

doc: lexer-doc parser-doc fo-lexer-doc fo-parser-doc java-doc
    
clear-ast-classes:
	@ rm -f $(AST_CLASSES_DIR)/*.class

compile-ast: clear-ast-classes $(AST_SOURCES) $(CLASSES_DIR)
	@ echo $(DELIM0)
	@ echo "Compiling AST"
	@ echo $(DELIM1)
	@ $(JAVAC) -classpath $(COMPILE_CLASSPATH) -d "$(CLASSES_DIR)" $(AST_SOURCES) 2>$(JAVAC_LOG_FILE) || (cat $(JAVAC_LOG_FILE) && exit 1)

compile: $(CLASSES) $(CORE_CLASSES)

$(CORE_CLASSES) : $(CLASSES_DIR) $(CORE_SOURCES)
#	@ echo $(CLASSES)
	@ echo $(DELIM0)
	@ echo "Compiling project core"
	@ echo $(DELIM1)
	@ $(JAVAC) -Xlint:unchecked -classpath $(COMPILE_CLASSPATH) -d "$(CLASSES_DIR)" $(CORE_SOURCES) 2>$(JAVAC_LOG_FILE) || (cat $(JAVAC_LOG_FILE) && exit 1)

$(CLASSES) : $(CLASSES_DIR) $(SOURCES) $(CORE_JAR_FILE)
#	@ echo $(CLASSES)
	@ echo $(DELIM0)
	@ echo "Compiling project"
	@ echo $(DELIM1)
	@ $(JAVAC) -Xlint:unchecked -classpath $(COMPILE_CLASSPATH):$(CORE_JAR_FILE) -d "$(CLASSES_DIR)" $(SOURCES) 2>$(JAVAC_LOG_FILE) || (cat $(JAVAC_LOG_FILE) && exit 1)
    
resources: $(RESOURCE_FILES)

$(RESOURCE_FILES): $(CLASSES_DIR) $(SRC_RESOURCE_FILES)
	@ echo $(DELIM0)
	@ echo "Copying resources"
	@ echo $(DELIM1)
	@ cp -R -L $(RESOURCE_DIR)/* $(CLASSES_DIR)
#@ cd $(RESOURCE_DIR) && cp -R -L $(shell cd $(RESOURCE_DIR) 1>/dev/null 2>&1 && find -L ./ -type f ! -path '*.svn*') $(CLASSES_DIR)
#	@ cd $(BASE_DIR)
	
#	echo $(RESOURCE_FILES)
#	echo $(SRC_RESOURCE_FILES)
	
#/ && cp -v -R --parents $(shell find . -type f ! -name '*.svn*') $(CLASSES_DIR)
#@ cd $(BASE_DIR) 
	
#	@ for i in $(SRC_RC_FILES); do \
#	    j=$$(echo $$i | sed -e 's/src/classes/g'); \
#	    mkdir -p $$(dirname $$j); \
#	    cp -r $$i $$j; done



core-jar: $(CORE_JAR_FILE)

$(CORE_JAR_FILE): $(CORE_CLASSES) $(RESOURCE_FILES) $(CORE_MANIFEST_FILE)
	@ echo $(DELIM0)
	@ echo "Building runtime jar ($(CORE_JAR_FILE_NAME))"
	@ echo $(DELIM1)
	@ $(JAR) cvmf $(CORE_MANIFEST_FILE) $(CORE_JAR_FILE) -C $(CLASSES_DIR) $(CORE_PACKAGE_SUBDIR)

jar: $(JAR_FILE)

$(JAR_FILE): $(CLASSES) $(RESOURCE_FILES) $(MANIFEST_FILE)
	@ echo $(DELIM0)
	@ echo "Building runtime jar ($(JAR_FILE_NAME))"
	@ echo $(DELIM1)
	@ $(JAR) cvmf $(MANIFEST_FILE) $(JAR_FILE) -C $(CLASSES_DIR) .
#	@ cd $(CLASSES_DIR) && $(JAR) cvmf $(MANIFEST_FILE) $(JAR_FILE) *
#	@ cd $(BASE_DIR)

generateLogics: $(JAR_FILE) $(TEST_CLASSES_DIR) $(CORE_JAR_FILE)
	@ echo $(DELIM0)
	@ echo "Generating logics"
#	@ echo $(DELIM1)
	@ for D in "$(TEST_LOGIC_DIRS)"; do echo $(DELIM1); echo "Generating $${D}"; java -cp $(RUNTIME_CLASSPATH) mettel.MettelGenerator -i "$(TEST_EXAMPLES_DIR)/$${D}/$${D}.s" -p "$(TEST_EXAMPLES_DIR)/$${D}/$${D}.properties" -d "test/output"; done
	
generateParsers: generateLogics
#	@ TEST_LOGIC_PARSERS = $(shell find $(TEST_OUTPUT_DIR) -name '*.g')
#	@ TEST_LOGIC_SOURCES = $(shell find $(TEST_OUTPUT_DIR) -name '*.java')
	@ echo $(DELIM0)
	@ echo "Generating parsers for logics"
	@ echo $(DELIM1)
	@for P in $(shell find $(TEST_OUTPUT_DIR) -name '*.g'); do \
		echo "Generating parser from \"$${P}\""; \
		java -cp $(COMPILE_CLASSPATH) org.antlr.Tool "$${P}" && \
	    rm -f -v "$$(basename $${P} .g).tokens"; done
#	@rm -f -v *.tokens
	
compileLogics: generateParsers
	@ echo $(DELIM0)
	@ echo "Compiling sources for logics"
	@ echo $(DELIM1)
	@ $(JAVAC) -classpath $(LOGIC_GENERATION_CLASSPATH):$(LIB_DIR)/junit.jar -d $(TEST_CLASSES_DIR) $(shell find $(TEST_OUTPUT_DIR) -name '*.java')
	
compileLogics-alone:
	@ echo $(DELIM0)
	@ echo "Compiling sources for logics"
	@ echo $(DELIM1)
	@ $(JAVAC) -classpath $(LOGIC_GENERATION_CLASSPATH):$(LIB_DIR)/junit.jar -d $(TEST_CLASSES_DIR) $(shell find $(TEST_OUTPUT_DIR) -name '*.java')	

generate: compileLogics

$(TEST_CLASSES): $(TEST_SOURCES) $(JAR_FILE) $(TEST_CLASSES_DIR) $(CORE_JAR_FILE)
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

test-jar-alone: $(TEST_CLASSES)
	@ echo $(DELIM0)
	@ echo "Building runtime jar (test.jar)"
	@ echo $(DELIM1)
	@ $(JAR) cvf $(TEST_JAR_FILE) -C $(TEST_CLASSES_DIR) .
#	@ cd $(TEST_CLASSES_DIR) && $(JAR) cvf $(TEST_JAR_FILE) *
#	@ cd $(BASE_DIR)

$(TEST_JAR_FILE): $(TEST_CLASSES) generate
	@ echo $(DELIM0)
	@ echo "Building runtime jar (test.jar)"
	@ echo $(DELIM1)
	@ $(JAR) cvf $(TEST_JAR_FILE) -C $(TEST_CLASSES_DIR) .
#	@ cd $(TEST_CLASSES_DIR) && $(JAR) cvf $(TEST_JAR_FILE) *
#	@ cd $(BASE_DIR)

test-jar: $(TEST_JAR_FILE)

define allTests
$(JAVA) -classpath .:$(TEST_CLASSPATH) $(JAVA_TEST_OPTIONS)	mettel.AllTests 
#2>$(TEST_ERR_FILE) || (cat $(TEST_ERR_FILE) && exit 1)
endef

test: $(TEST_JAR_FILE)
	@ echo $(DELIM0)
	@ echo "Testing"
	@ echo $(DELIM1)
	@ $(allTests)
#2>$(TEST_LOG_FILE) || (cat $(TEST_LOG_FILE) && exit 1)

test-alone:
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
#	@ $(JAVA_HOME)/bin/java -classpath .:$(RUNTIME_CLASSPATH) $(JAVA_TEST_OPTIONS) mettel.parser.MettelParser || cat examples/formulae.err
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
	 


	