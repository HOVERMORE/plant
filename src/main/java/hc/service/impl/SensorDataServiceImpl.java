package hc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hc.mapper.SensorDataMapper;
import hc.pojos.SensorData;
import hc.service.ISensorDataService;
import org.springframework.stereotype.Service;

@Service
public class SensorDataServiceImpl extends ServiceImpl<SensorDataMapper, SensorData> implements ISensorDataService {
}
