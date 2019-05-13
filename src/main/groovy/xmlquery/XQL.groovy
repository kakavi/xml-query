package xmlquery

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
        def nodePrinter = new XmlNodePrinter(new PrintWriter(stringWriter),"")
        nodePrinter.setNamespaceAware(true)
        nodePrinter.print(xml)
        def newXml = stringWriter.toString()
        return newXml
    }

    String queryAsOneString() {
        def xml = new XmlParser().parseText(xmlString)
        xml.depthFirst().findAll { p ->
            if (!p."$conditionNode") return
            p."${conditionNode}"[0].text().equals(oldValue)
        }.each { p ->
            p."${nodeToUpdate}"[0].value = newValue
        }
        def stringWriter = new StringWriter()
        def nodePrinter = new XmlNodePrinter(new PrintWriter(stringWriter),"")
        nodePrinter.setNamespaceAware(true)
        nodePrinter.print(xml)
        def newXml = stringWriter.toString().readLines().join()
        return newXml
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
