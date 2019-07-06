# xml-query
Groovy Library to update and query xml file

## Features
    * Querying Xml document using sql query like structure
    * Adding elements to xml
    * Modifying xml tag names
    
### Examples
``` def originalXml = """<?xml version="1.0" encoding="UTF-8"?>
                        <iics_survey_school_questionnaire_v1>
                                <visit>baseline</visit>
                                    <school_prompt>
                                        <school_name>null</school_name>
                                        <__code>MAS/KIM/0002</__code>
                                    </school_prompt>
                        </iics_survey_school_questionnaire_v1>"""
```
to add <groupCode>FG12</groupCode> to the Original Xml,
 ```def result = new XQL()
                .alterXML(sampleXml3)
                .add("groupCode")
                .withDefaultVal("FG12")
                .insertBefore("visit")
 ```
 To learn more how this library works please check out the unit Test.
## Installation

### Maven

``` 
<dependency>
     <groupId>xml-query</groupId>
     <artifactId>xml-query</artifactId>
     <version>${version}</version>
</dependency>
```

### Gradle

`compile 'xml-query:xml-query:${version}'`