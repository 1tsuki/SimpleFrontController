package com.astrider.sfc.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.astrider.sfc.helper.annotation.Valid;
import com.astrider.sfc.model.vo.BaseVO;

public class Validator<T extends BaseVO> {
    private HashMap<String, String> errorMessage = new HashMap<String, String>();
    private T                       vo;

    public Validator(T vo) {
        this.vo = vo;
    }

    public HashMap<String, String> getErrorMessage() {
        return errorMessage;
    }

    public boolean validate() {
        boolean result = true;

        Field[] declaredFields = vo.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            result = validateField(f) && result;
        }

        return result;
    }

    private boolean validateField(Field f) {
        boolean result = true;
        Valid v = f.getAnnotation(Valid.class);
        if (v == null) {
            return true;
        }

        for (Field vf : v.getClass().getDeclaredFields()) {
            vf.setAccessible(true);
            try {
                Method vm = (Method) vf.get(v);
                if (vm.getReturnType().equals(Boolean.TYPE) && !vm.getName().equals("equals")) {
                    if ((Boolean) vm.invoke(v)) {
                        Method method = this.getClass().getMethod(vm.getName(), Field.class, Valid.class);
                        result = (Boolean) method.invoke(this, f, v) && result;
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /*
     * validation methods
     */
    public boolean isNotNull(Field f) {
        try {
            return f.get(vo) != null;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    public boolean isNotNull(Field f, Valid v) {
        return isNotNull(f);
    }

    public boolean isNotBlank(Field f, Valid v) {
        try {
            String value = String.valueOf(f.get(vo));
            return StringUtils.isNotEmpty(value);
        } catch (ClassCastException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    public boolean isLength(Field f, Valid v) {
        try {
            String value = String.valueOf(f.get(vo));
            return v.length() == value.length();
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    public boolean isMinLength(Field f, Valid v) {
        try {
            String value = String.valueOf(f.get(vo));
            return v.minLength() <= value.length();
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    public boolean isMaxLength(Field f, Valid v) {
        try {
            String value = String.valueOf(f.get(vo));
            return value.length() <= v.maxLength();
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    public boolean isMin(Field f, Valid v) {
        try {
            int value = (Integer) f.get(vo);
            return v.min() <= value;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    public boolean isMax(Field f, Valid v) {
        try {
            int value = (Integer) f.get(vo);
            return value <= v.max();
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    public boolean isRegexp(Field f, Valid v) {
        return checkRegexp(f, v.regexp());
    }

    public boolean isUrl(Field f, Valid v) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return checkRegexp(f, regex);
    }

    public boolean isEmail(Field f, Valid v) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return checkRegexp(f, regex);
    }

    public boolean isPhone(Field f, Valid v) {
        String regex = "^\\d{1,4}?-\\d{1,4}?-\\d{1,4}$";
        return checkRegexp(f, regex);
    }

    private boolean checkRegexp(Field f, String regex) {
        try {
            String value = (String) f.get(vo);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);

            return matcher.matches();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
