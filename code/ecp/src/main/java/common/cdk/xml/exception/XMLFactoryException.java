/*    */ package common.cdk.xml.exception;
/*    */ 
/*    */ public class XMLFactoryException extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String errorMsg;
/*    */   private Throwable throwable;
/*    */ 
/*    */   public XMLFactoryException(String errorMsg, Throwable throwable)
/*    */   {
/* 15 */     super(errorMsg, throwable);
/* 16 */     this.errorMsg = errorMsg;
/* 17 */     this.throwable = throwable;
/*    */   }
/*    */   public XMLFactoryException(String errorMsg) {
/* 20 */     super(errorMsg);
/* 21 */     this.errorMsg = errorMsg;
/*    */   }
/*    */   public String getErrorMsg() {
/* 24 */     return this.errorMsg;
/*    */   }
/*    */   public void setErrorMsg(String errorMsg) {
/* 27 */     this.errorMsg = errorMsg;
/*    */   }
/*    */   public Throwable getThrowable() {
/* 30 */     return this.throwable;
/*    */   }
/*    */   public void setThrowable(Throwable throwable) {
/* 33 */     this.throwable = throwable;
/*    */   }
/*    */ }

/* Location:           F:\Program Files\workspace2\ats\WebRoot\WEB-INF\lib\common-xml.1.0.jar
 * Qualified Name:     common.cdk.xml.exception.XMLFactoryException
 * JD-Core Version:    0.6.0
 */