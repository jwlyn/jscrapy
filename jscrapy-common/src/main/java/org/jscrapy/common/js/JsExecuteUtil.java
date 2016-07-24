package org.jscrapy.common.js;

import org.apache.commons.lang3.StringUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by cxu on 2016/2/23.
 */
public class JsExecuteUtil {

    private static final String JS_ENGINE_NAME = "javascript";

    private ScriptEngine jsEngine;

    public JsExecuteUtil() {
        jsEngine = new ScriptEngineManager().getEngineByName(JS_ENGINE_NAME);
    }

    /**
     * 执行脚本
     *
     * @param script
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    public void executeScript(String script)  throws ScriptException, NoSuchMethodException{
        this.doExecute(script, null);
    }

    /**
     * 执行脚本方法
     *
     * @param script 脚本内容
     * @param funName 方法名称
     * @param args 参数列表
     * @return 脚本返回对象
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    public JsExecuteResult executeFunction(String script, String funName, Object... args) throws ScriptException, NoSuchMethodException {
        JsExecuteResult result = this.doExecute(script, funName, args);
        return result;
    }

    /**
     * 获取参数
     * @param arg
     * @return
     */
    public JsExecuteResult getParameter(String arg){
        JsExecuteResult result = new JsExecuteResult();
        if (StringUtils.isBlank(arg) && null == jsEngine) {
            return result;
        }
        result.setResult(String.valueOf(jsEngine.get(arg)));
        result.setIsSuccess(Boolean.TRUE);
        return result;
    }

    /**
     * 执行脚本
     * @param script
     * @param funName
     * @param args
     * @return
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    private JsExecuteResult doExecute(String script, String funName, Object... args) throws ScriptException, NoSuchMethodException{
        JsExecuteResult result = new JsExecuteResult();

        if(null == jsEngine){
            result.setMessage("您的JDK不支持javascript引擎");
            return result;
        }
        if(StringUtils.isBlank(script)){
            result.setMessage("javascript source code is empty");
            return result;
        }

        if (StringUtils.isBlank(funName) && (null == args || args.length == 0)) {//只有script
            result.setResult(jsEngine.eval(script));
            result.setIsSuccess(Boolean.TRUE);
        }

        if (StringUtils.isNotBlank(funName)) {
            jsEngine.eval(script);
            Invocable inv = (Invocable) jsEngine;
            result.setResult(inv.invokeFunction(funName, args));
            result.setIsSuccess(Boolean.TRUE);
        }

        return result;
    }

}
