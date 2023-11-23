package com.qtone.util;

import com.qtone.util.saveSeniorCardData.SaveData;
import com.qtone.util.saveSeniorCardData.SaveJobCardData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaveSeniorCardDataTests {

    @Resource
    SaveData saveData;
    @Resource
    SaveJobCardData saveJobCardData;

    @Test
    public void testToProdCard() throws Exception {
//        saveData.saveLocationInfo();
        saveData.saveLocationBound();
    }

    @Test
    public void saveJobCardData() throws Exception {
//        saveJobCardData.saveLocationInfo();
        saveJobCardData.saveLocationBound();
    }

}
