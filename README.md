# Syndrome Minifier

Syndrome Minifier is javascript and css minifier. 

### Usage

Create a xml file with name syndrome.xml. An example of this file like below:

```sh
<?xml version="1.0" encoding="utf-8" standalone="no"?>
<projects>
    <project>
        <name>my-app</name>
        <version>1.0.1</version>
        <build>1</build>
        <script>
            <File>lib/angular/1.4.8/angular.js</File>
            <File>lib/angular/1.4.8/angular-animate.min.js</File>
            <File>lib/angular/1.4.8/angular-route.min.js</File>
            <File>lib/angular/1.4.8/angular-aria.min.js</File>
            <File>lib/angular/1.4.8/angular-messages.min.js</File>
            <File>lib/angular/1.4.8/angular-material.js</File>
            <File>app/app.js</File>
			<Query match="*.js" root="/app"/>
        </script>
		<style>
            <File>lib/angular/1.4.8/angular-material.css</File>
            <File>app/app.css</File>
		</style>
    </project>
</projects>
```
Exe version of project [available][df1]. Extract it and open parent folder via command line and type

```sh
compiler d:\workspace\my-project\
```
where 'd:\workspace\my-project\' is the root of the your web project that contains syndrome.xml definition xml.

Note that we strongly recommend that open command line as root user in Linux like system and as Administrator in Windows.

File math properties
- Path in <File> tag are represents single files
- Query tag is a search tag and have two attribute called "root" and "match". "root" attribute represents root of the search path that the compiler start to search. "match" attribute represents a regex pattern to match file name. If you want to match all files with any name, you can extraordinarily write "*" like in the example config xml.