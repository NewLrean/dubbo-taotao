package com.taotao.user.exception;

/**
 * Created by 蒋志鹏 on 2018/7/6.
 */
public class GobalException extends Exception{
    /** serialVersionUID*/

    private static final long serialVersionUID = -5212079010855161498L;

    public GobalException(String message){

        super(message);

        this.message = message;

    }



//异常信息

    private String message;



    public String getMessage() {

        return message;

    }



    public void setMessage(String message) {

        this.message = message;

    }


}
