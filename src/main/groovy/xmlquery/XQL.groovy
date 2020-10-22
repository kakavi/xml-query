package xmlquery

import groovy.xml.DOMBuilder
import groovy.xml.XmlUtil
import groovy.xml.dom.DOMCategory
import org.w3c.dom.Element
import org.w3c.dom.Node

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
        def doc = DOMBuilder.parse(new StringReader(xmlString), false, true)
        def root = doc.documentElement
        use(DOMCategory) {
            def nodesToChange = root.depthFirst().findAll { node ->
                if (!node."'$conditionNode'" ) return
                ((Element) node).getElementsByTagName(conditionNode).text().equals(oldValue)
            }
            nodesToChange.each { node ->
                if (((Element) node).nodeName.equals(root.nodeName) && ((Element) node).nodeType != Node.ELEMENT_NODE) return
                ((Element) node).getElementsByTagName(nodeToUpdate).item(0).textContent = newValue
            }

        }
        def result = XmlUtil.serialize(root).trim()
//        windows appends \r to new line so we need to remove
        result = result.replaceAll("\\r", "")
        return result
    }

    String selectWhere() {
        def xml = new XmlParser(false, true).parseText(xmlString)
        return xml.depthFirst().find { node ->
            if (!node."$conditionNode") return
            if (conditionNode) {
                node."${conditionNode}"[0].text().equals(oldValue)
            }
        }."${conditionNode}"[0].text()
    }

    String selectValue() {
        def xml = new XmlParser(false, true).parseText(xmlString)
        return xml.depthFirst().find { node ->
            if (!node."$selectNode") return
            node."${selectNode}"
        }."${selectNode}".text()
    }

    def select(String node) {
        this.selectNode = node
        return this
    }

    def update(String xml) {
        this.xmlString = xml
        return this
    }

    def alterXML(String xml) {
        return new XQLALter(xml)
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
