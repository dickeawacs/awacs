/*     */ package common.cdk.xml.reader;
/*     */ 
/*     */ import common.cdk.xml.exception.XMLFactoryException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.Node;
/*     */ import org.dom4j.io.OutputFormat;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.io.XMLWriter;
/*     */ 
/*     */ public class XMLFactory
/*     */ {
/*  27 */   private static Logger logger = Logger.getLogger(XMLFactory.class);
/*     */   public String filePath;
/*     */   public File xmlFile;
/*     */   public FileInputStream fileInputStream;
/*     */   public FileOutputStream fileOutputStream;
/*     */   public Document document;
/*     */ 
/*     */   public XMLFactory()
/*     */   {
/*     */   }
/*     */ 
/*     */   public XMLFactory(File xmlFile)
/*     */   {
/*  40 */     this.xmlFile = xmlFile;
/*     */   }
/*     */ 
/*     */   public XMLFactory(String filePath) {
/*  44 */     this.filePath = filePath;
/*  45 */     this.xmlFile = new File(this.filePath);
/*     */   }
/*     */ 
/*     */   public XMLFactory(Document document) {
/*  49 */     this.document = document;
/*     */   }
/*     */ 
/*     */   public XMLFactory(FileInputStream fileInputStream) {
/*  53 */     this.fileInputStream = fileInputStream;
/*     */   }
/*     */ 
/*     */   public XMLFactory(FileOutputStream fileOutputStream) {
/*  57 */     this.fileOutputStream = fileOutputStream;
/*     */   }
/*     */ 
/*     */   public String getFilePath() {
/*  61 */     return this.filePath;
/*     */   }
/*     */ 
/*     */   public void setFilePath(String filePath) {
/*  65 */     this.filePath = filePath;
/*     */   }
/*     */ 
/*     */   public File getXmlFile() {
/*  69 */     return this.xmlFile;
/*     */   }
/*     */ 
/*     */   public void setXmlFile(File xmlFile) {
/*  73 */     this.xmlFile = xmlFile;
/*     */   }
/*     */ 
/*     */   public FileInputStream getFileInputStream() {
/*  77 */     return this.fileInputStream;
/*     */   }
/*     */ 
/*     */   public void setFileInputStream(FileInputStream fileInputStream) {
/*  81 */     this.fileInputStream = fileInputStream;
/*     */   }
/*     */ 
/*     */   public FileOutputStream getFileOutputStream() {
/*  85 */     return this.fileOutputStream;
/*     */   }
/*     */ 
/*     */   public void setFileOutputStream(FileOutputStream fileOutputStream) {
/*  89 */     this.fileOutputStream = fileOutputStream;
/*     */   }
/*     */ 
/*     */   public Document getDocument()
/*     */     throws DocumentException, XMLFactoryException
/*     */   {
/*  99 */     if (this.document == null) {
/* 100 */       if (this.fileInputStream != null) {
/*     */         try {
/* 102 */           SAXReader reader = new SAXReader();
/* 103 */           this.document = reader.read(this.fileInputStream);
/* 104 */           this.fileInputStream.close();
/*     */         } catch (IOException e) {
/* 106 */           this.fileInputStream = null;
/* 107 */           throw new XMLFactoryException("close inputStream was  failed");
/*     */         }
/* 109 */       } else if (this.xmlFile != null) {
/* 110 */         SAXReader reader = new SAXReader();
/* 111 */         this.document = reader.read(this.xmlFile);
/* 112 */       } else if (this.filePath != null) {
/* 113 */         SAXReader reader = new SAXReader();
/* 114 */         this.document = reader.read(new File(this.filePath));
/*     */       } else {
/* 116 */         throw new XMLFactoryException("this xml get Document failed ,because the file can't found or haven't fileInputStream!");
/*     */       }
/*     */     }
/*     */ 
/* 120 */     return this.document;
/*     */   }
/*     */ 
/*     */   public void setDocument(Document document) {
/* 124 */     this.document = document;
/*     */   }
/*     */ 
/*     */   public List<?> findBy_Xpath(Document doc, String by)
/*     */   {
/* 133 */     return doc.selectNodes(by);
/*     */   }
/*     */ 
/*     */   public List<?> findBy_Xpath(Element element, String by)
/*     */   {
/* 142 */     return element.selectNodes(by);
/*     */   }
/*     */ 
/*     */   public Node findBy_Xpath_Single(Element element, String by)
/*     */   {
/* 151 */     return element.selectSingleNode(by);
/*     */   }
/*     */ 
/*     */   public String saveXMLFile(String path, InputStream in)
/*     */   {
/* 156 */     return path;
/*     */   }
/*     */ 
/*     */   public String saveXMLFile(String path)
/*     */   {
/* 165 */     return path;
/*     */   }
/*     */ 
/*     */   public String saveXMLFile(InputStream in)
/*     */   {
/* 175 */     return this.filePath;
/*     */   }
/*     */ 
/*     */   public boolean saveXMLFile()
/*     */     throws IOException
/*     */   {
/* 182 */     return doSave();
/*     */   }
/*     */ 
/*     */   private boolean doSave()
/*     */     throws IOException
/*     */   {
/* 192 */     boolean end = false;
/* 193 */     XMLWriter xmlwt = null;
/*     */     try {
/* 195 */       OutputFormat fmt = OutputFormat.createPrettyPrint();
/* 196 */       fmt.setEncoding("UTF-8");
/* 197 */       xmlwt = new XMLWriter(new FileOutputStream(this.filePath), fmt);
/* 198 */       xmlwt.write(this.document);
/* 199 */       end = true;
/*     */     } catch (Exception e) {
/* 201 */       logger.info(e);
/*     */     } finally {
/* 203 */       if (xmlwt != null) {
/* 204 */         xmlwt.close();
/*     */       }
/*     */     }
/* 207 */     return end;
/*     */   }
/*     */ 
/*     */   public boolean doSave(String path)
/*     */     throws IOException
/*     */   {
/* 216 */     boolean end = false;
/* 217 */     XMLWriter xmlwt = null;
/*     */     try {
/* 219 */       OutputFormat fmt = OutputFormat.createPrettyPrint();
/* 220 */       fmt.setEncoding("UTF-8");
/* 221 */       xmlwt = new XMLWriter(new FileOutputStream(path), fmt);
/* 222 */       xmlwt.write(this.document);
/* 223 */       end = true;
/*     */     } catch (Exception e) {
/* 225 */       logger.info(e);
/*     */     } finally {
/* 227 */       if (xmlwt != null) {
/* 228 */         xmlwt.close();
/*     */       }
/*     */     }
/* 231 */     return end;
/*     */   }
/*     */ }

/* Location:           F:\Program Files\workspace2\ats\WebRoot\WEB-INF\lib\common-xml.1.0.jar
 * Qualified Name:     common.cdk.xml.reader.XMLFactory
 * JD-Core Version:    0.6.0
 */