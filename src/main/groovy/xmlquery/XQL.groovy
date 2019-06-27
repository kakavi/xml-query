package xmlquery

import groovy.xml.DOMBuilder
import groovy.xml.XmlUtil
import groovy.xml.dom.DOMCategory

class XQL {

    String xmlString
    String selectNode
    String conditionNode
    String nodeToUpdate
    String oldValue
    String newValue
    String rootNode


    XQL(String rootNode) {
        this.rootNode = rootNode
    }

    XQL() {
//
    }

    String queryFormated() {
        def xml = new XmlParser().parseText(xmlString)
        xml.depthFirst().findAll { p ->
            if (!p."$conditionNode") return
            p."${conditionNode}"[0].text().equals(oldValue)
        }.each { p ->
            p."${nodeToUpdate}"[0].value = newValue
        }
        def stringWriter = new StringWriter()
        def nodePrinter = new XmlNodePrinter(new PrintWriter(stringWriter), "")
        nodePrinter.setNamespaceAware(true)
        nodePrinter.print(xml)
        def newXml = stringWriter.toString()
        return newXml
    }

    String queryAsOneString() {
        def xml = new XmlParser(false, true).parseText(xmlString)
        xml.depthFirst().findAll { p ->
            if (!p."$conditionNode") return
            p."${conditionNode}"[0].text().equals(oldValue)
        }.each { p ->
            p."${nodeToUpdate}"[0].value = newValue
        }
        def stringWriter = new StringWriter()
        def nodePrinter = new XmlNodePrinter(new PrintWriter(stringWriter), "")
        nodePrinter.setNamespaceAware(true)
        nodePrinter.print(xml)
        def newXml = stringWriter.toString().readLines().join()
        return newXml
    }

    String queryKeepNameSpaces() {
        def doc = DOMBuilder.parse(new StringReader(xmlString),false,true)
        def root = doc.documentElement
        use(DOMCategory) {
            root.depthFirst().findAll {node->
                if(!node."$conditionNode") return
                node."${conditionNode}".text().equals(oldValue)
            }.each {node->
                node."${nodeToUpdate}"*.value = newValue
            }
        }
        def result = XmlUtil.serialize(root).trim()
//        windows appends \r to new line so we need to remove
        result = result.replaceAll("\\r","")
        return result
    }

    String selectValue(){
        def xml = new XmlParser(false, true).parseText(xmlString)
        return xml.depthFirst().find {node->
            if(!node."$conditionNode") return
            node."${conditionNode}"[0].text().equals(oldValue)
        }."${conditionNode}"[0].text()
    }

    def select(String node) {
        this.selectNode = node
        return this
    }

    def update(String xml) {
        this.xmlString = xml
        return this
    }

    def from(String xml) {
        this.xmlString = xml
        return this
    }

    def set(String nodeToUpdate) {
        this.nodeToUpdate = nodeToUpdate
        return this
    }

    def where(String conditionNode) {
        this.conditionNode = conditionNode
        return this
    }

    def isEqualTo(String oldVal) {
        this.oldValue = oldVal
        return this
    }

    def to(String newVal) {
        this.newValue = newVal
        return this
    }


}
