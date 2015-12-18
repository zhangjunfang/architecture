package Rpc.exam.client;

import com.ocean.rpc.common.SimpleMode;

public interface IExam2 {
    @SimpleMode(true)
    User[] getUserList();
}
