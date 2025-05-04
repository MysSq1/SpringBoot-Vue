//package cn.smxy.newsserver;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@SpringBootTest
//public class MapperText {
//
//
//        @Autowired
//        private LabinfoMapper labinfoMapper;
//
//        @Test
//        public void test() {
//            List<Labinfo> peopleList = labinfoMapper.findAll();
//            System.out.println(peopleList);
//        }
//
//        @Test
//        public void test7() {
//            labinfoMapper.add(new Labinfo(3,"1234","12412","1241","12312"));
//            System.out.println("添加成功");
//        }
//        @Test
//        public void test3() {
//            labinfoMapper.update(new Labinfo(2,"123423435","20210861209","1234","21312"));
//            System.out.println("信息修改成功");
//        }
//        @Test
//        public void test2() {
//            labinfoMapper.delBy(1);
//            System.out.println("删除成功");
//        }
//
//}
