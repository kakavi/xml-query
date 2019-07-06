package xmlquery

import groovy.xml.DOMBuilder
import groovy.xml.XmlUtil
import groovy.xml.dom.DOMCategory
import org.w3c.dom.NodeList

class XQLALter {
    private String xmlString
    private String tagName
    private String defaultValue
    private String newTagName

    XQLALter(String xmlString) {
        this.xmlString = xmlString
    }

    def add(String tagName){
        this.tagName = tagName
        return this
    }

    def modify(String tagName){
        this.tagName = tagName
        return this
    }

    def withDefaultVal(String defaultValue){
        this.defaultValue = defaultValue
        return this
    }


    def insertBefore(String beforeNode,String parentNode=""){
        def doc = DOMBuilder.parse(new StringReader(xmlString), false, true)
        def root = doc.documentElement
        use(DOMCategory){
            def bfNode = root.depthFirst().find {node->
                node.name().equals(beforeNode)
            }
            def newNode = doc.createElement(tagName)
            newNode.appendChild(doc.createTextNode(defaultValue))
            if(parentNode.equals("")){
                root.insertBefore(newNode,bfNode)
            }else{
                def ownerNode = bfNode.ownerNode
                ownerNode.insertBefore(newNode,bfNode)
            }
        }
        def result = XmlUtil.serialize(root).trim()
        result = result.replaceAll("\\r", "")
        return result
    }

    def execute(){
        def doc = DOMBuilder.parse(new StringReader(xmlString), false, true)
        def root = doc.documentElement
        use(DOMCategory){
            root.appendNode(tagName,defaultValue)
        }
        def result = XmlUtil.serialize(root).trim()
        result = result.replaceAll("\\r", "")
        return result
    }

    def toNewName(String newTagName){
        this.newTagName = newTagName
        return this
    }

    def executeModify(){
        def doc = DOMBuilder.parse(new StringReader(xmlString), false, true)
        def root = doc.documentElement
        use(DOMCategory){
            doc.getElementsByTagName(tagName).each {element->
                doc.renameNode(element,null,newTagName)
            }
        }
        def result = XmlUtil.serialize(root).trim()
        result = result.replaceAll("\\r", "")
        return result
    }
}
