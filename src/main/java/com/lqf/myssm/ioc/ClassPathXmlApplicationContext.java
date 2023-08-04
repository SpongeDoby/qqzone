package com.lqf.myssm.ioc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory{
    private Map<String,Object> beanMap=new HashMap<>();

    public ClassPathXmlApplicationContext(){
        this("applicationContext.xml");
    }
    public ClassPathXmlApplicationContext(String filename){
        if(filename==null){
            filename="applicationContext.xml";
        }
        try {
            /**
             * 初始化的功能：解析xml配置文件，实例化所有注册在配置文件中的controller，使用一个容器保存id和实例对象。
             * 而且可以看出来它是单例的，这也是Spring和SpringMVC的默认模式
             */
            //jdk有自带DOM解析器，下面用到的类都是抽象类或接口，在解析器解析xml时就会实例化这些抽象类和接口并定义相关属性和行为
            //读取配置文件applicationContext.xml,Maven的默认资源路径为/src/main/resources
            InputStream applicationContext = this.getClass().getClassLoader().getResourceAsStream(filename);
            //新建DocumentBuilderFactory对象
            DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
            //获取DocumentBuilder
            DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
            //解析配置文件获得Document对象
            Document document = documentBuilder.parse(applicationContext);
            //读取xml节点，获取<bean>节点列表
            NodeList beanNodeList = document.getElementsByTagName("bean");
            //遍历列表，逐个bean获取id和class,实例化对应的Controller，绑定id和实例对象
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                //判断是否为元素节点
                if(beanNode.getNodeType()==Node.ELEMENT_NODE){
                    //Element是Node子接口
                    Element beanElement=(Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String beanClass = beanElement.getAttribute("class");
                    //加载对应类并实例化
                    Object beanObj = Class.forName(beanClass).getConstructor().newInstance();
                    beanMap.put(beanId,beanObj);
                }
            }
            //组装bean之间的依赖关系，读取<property>标签
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                //逐个bean遍历
                if(beanNode.getNodeType()==Node.ELEMENT_NODE){
                    //Element是Node子接口
                    Element beanElement=(Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String beanClass = beanElement.getAttribute("class");
                    //读取<property>标签
                    NodeList propertyList = beanElement.getElementsByTagName("property");
                    for (int j = 0; j < propertyList.getLength(); j++) {
                        Node propertyNode = propertyList.item(j);
                        if(propertyNode.getNodeType()==Node.ELEMENT_NODE){
                            //读取property中的属性名name和引用的组件名
                            Element propertyElement=(Element) propertyNode;
                            String propertyName = propertyElement.getAttribute("name");
                            String ref = propertyElement.getAttribute("ref");
                            //组装依赖关系,令bean中类的实例对象的property属性值为ref对应的实例对象
                            Field declaredField = Class.forName(beanClass).getDeclaredField(propertyName);
                            declaredField.setAccessible(true);
                            Object beanObj = beanMap.get(beanId);
                            Object refObj = beanMap.get(ref);
                            declaredField.set(beanObj,refObj);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
