## cli-annotations
=======================

Parsing input is so much fun. How else to explain why we've all taken the time to roll our own implementations of
both command line and property file based configuration readers?

Or not. How about we switch to something more comfortable: annotate a POJO and read your configuration into it with
three lines of extra code?

#### Easier, more familiar configuration.
CLI Annotations is a misnomer. The project started as "just" an extension of the excellent JOptSimple. And it was good.
Parsing the command line straight into an annotated POJO was pretty slick, all by itself.

But, eventually, all applications evolves to <strike>send mail</strike> become a web-app. Which means
configuring from a resource property file, or perhaps just a property file in the startup directory. So
cli-annotations has evolved to read properties files. With just one more annotation and a few lines of code you can
switch from reading configuration off the command line to reading configuration from one or more properties files.
And the extra annotation isn't even required - of course, your properties file will use some admittedly strange keys
if you accept the default.

That's really slick.

#### Some examples

##### A configuration POJO

As indicated by the class name, this class was borrowed from the unit tests. It's extra verbose,
filling in many extra options for your viewing pleasure:

    public class ProcessorTestConfig {

        @CliOption(
                shortOption = "b",
                longOption = "booleanLongOption",
                propertyKey = "booleanPropertyKey",
                description = "A single boolean value"
        )
        public boolean booleanValue;

        @CliOption(
                shortOption = "i",
                longOption = "integerLongOption",
                propertyKey = "integerPropertyKey",
                description = "Simplest integer value."
        )
        public int integerValue;

        @CliOption(
                shortOption = "j",
                longOption = "integerListLongOption",
                propertyKey = "integerListPropertyKey",
                description = "List of integer values.",
                argument = @CliOptionArgument()
        )
        public List<Integer> integerList;

        @CliOption(
                shortOption = "k",
                longOption = "integerListDefaultsLongOption",
                propertyKey = "integerListDefaultsPropertyKey",
                description = "List of integer values with defaults.",
                argument = @CliOptionArgument(defaultValue = {"10", "20", "30"})
        )
        public List<Integer> integerListWithDefaults;
    }


##### Reading ProcessorTestConfig from a Properties File (e.g. $projectDir/src/main/resources/configuration.properties)

    public void SomeConfigurationMethod(){
        PropertyReaderModelProcessor<ProcessorTestConfig> processor =
                    new PropertyReaderModelProcessor<ProcessorTestConfig>(ProcessorTestConfig.class);
            Properties p = processor.addResourcePath("/configuration.properties", getClass());
            // if p is null, your resource path was wrong!
            ProcessorTestConfig config = processor.processInput();
            // do something with config.
    }

That's all you have to do to parse from a single properties file. Configuration.properties looks something like this:

    ~configuration.properties
        booleanPropertyKey=true
        integerPropertyKey=100
        integerListPropertyKey=30,40,50
        floatPropertyKey=10.10
        stringValuePropertyKey=foo'tacular


That's awesome.

But there's even more awesome. How about chained properties files?

##### Reading a configuration from multiple properties files

So here's the use case: Your local configuration is different from the configuration you ship with your product. It's
 a bit of an edge case, but hey, what are 3rd party libs for, if not to help with your esoteric workflows?

Let's say you have two configuration files:

    $projectDir/src/main/resources/configuration.properties
    $projectDir/src/main/resources/configuration.local.properties

Your .gitIgnore file keeps the .local.properties file from ever being committed; it exists only to override your
production configuration values.

We'll use the above listing for configuration.properties, and we'll use our local version to override a couple values:

    ~configuration.local.properties
        booleanPropertyKey=false
        integerPropertyKey=9999
        stringValuePropertyKey=qux'tacular

And we'll update our config method to read them into one configuration object:

    public void SomeConfigurationMethod(){
        PropertyReaderModelProcessor<ProcessorTestConfig> processor =
                    new PropertyReaderModelProcessor<ProcessorTestConfig>(ProcessorTestConfig.class);
            Properties p = processor.addResourcePath("/configuration.properties", getClass());
            // if p is null, your resource path was wrong! p only holds values from configuration.properties
            p = processor.addResourcePath("/configuration.local.properties", getClass());
            // if p is null, your resource path was wrong! p only holds values from configuration.local.properties
            // Use this feature to help debug your environment, if something seems awry.
            ProcessorTestConfig config = processor.processInput();
            // config is fully populated, with configuration.local.properties overriding values from
            // configuration.properties
    }


"But wait!" You cry. "We don't want to commit configuration.local.properties, or use it in production!"

Of course not. #addResourcePath (and the #addInputSource(InputStream) version) ignore null values. So you are
 free to add as many 'overrides' as you want - just make sure they don't make it into your builds!

There's no limit to the number of paths you can throw at it, and, as mentioned, there is also an "#addInputSource
(InputStream)" version of the #addResourcePath method so that you can point to the file system as well.


#### Reading from the command line

The command line is just as easy, and thanks to JOptSimple it will even generate most of your help listing for you.
So long as you use reasonable option names (in the annotations) you won't need to augment the help output by much,
even for user facing applications. Let's set up a main method for the same configuration POJO as we showed above,
with one difference - make ProcessorTestConfig extend CliConfig:

    public class ProcessorTestConfig extendsd CliConfig {
        // ...  body as before
    }

And now parse:

    public static void main(String[] args) throws Exception{
        JoptSimpleCliModelProcessor<ProcessorTestConfig> processor =
                new JoptSimpleCliModelProcessor<ProcessorTestConfig>(ProcessorTestConfig.class);
        ProcessorTestConfig config = processor.processInput(args);
        if(config.showHelp()) {
            processor.printHelp(out);
        }
        else{
            startYourProgram(config);
        }
    }

It really doesn't get much easier than that.


### Getting Jars

Branch the repo and then execute the gradle build:

    > gradle build

This will generate three jars; one for cli-annotations and one each for the CLI and PropertyReader plugins.

Both plugins dependon commons-lang:

    ~build.gradle
        ...
        compile 'commons-lang:commons-lang:2.6'

The CLI plugin needs JOptSimple:

    ~build.gradle
        ...
        compile 'net.sf.jopt-simple:jopt-simple:4.3'


### Have fun, and I'd love your feedback and/or pull requests.