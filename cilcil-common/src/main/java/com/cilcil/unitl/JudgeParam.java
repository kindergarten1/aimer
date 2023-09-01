package com.cilcil.unitl;

import com.cilcil.unitl.exception.CustomException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author SlyAimer
 * @Date 2023/9/1 17:24
 * @Version 1.0
 */
@Component
public class JudgeParam {
    public JudgeParam() {
    }

    public static Integer judgeCountExist(Integer count, String msg) {
        if (count <= 0) {
            throw new CustomException(msg);
        } else {
            return count;
        }
    }

    public static String paramIsNotNull(String param, String msg) {
        if (isNullOrUndefined(param)) {
            throw new CustomException(msg);
        } else {
            return param;
        }
    }

    public static <T> T entityIsNotNull(T param, String msg) {
        if (param == null) {
            throw new CustomException(msg);
        } else {
            return param;
        }
    }

    public static <T> List<T> listSizeMoreThan0(List<T> list, String msg) {
        if (list != null && list.size() > 0) {
            return list;
        } else {
            throw new CustomException(msg);
        }
    }

    public static boolean isNullOrUndefined(String param) {
        return param == null || param.trim().length() == 0 || param.trim().equalsIgnoreCase("undefined") || param.trim().equalsIgnoreCase("Null");
    }
}
