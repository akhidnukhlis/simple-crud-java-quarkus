package akhid.development.service;

import akhid.development.dto.CakeRequestDto;
import akhid.development.model.Cake;
import com.oracle.svm.core.annotate.Inject;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class CakeService {
    private static final Logger LOG = LoggerFactory.getLogger(CakeService.class);
    @Inject
    EntityManager em;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Map<String, Object> submit(CakeRequestDto dto) {
        LOG.info("service cake");
        
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String cakeId = insertData(dto, uuid);

        Map<String, Object> result = new HashMap<>();
        result.put("id", cakeId);
        return result;
    }

    @Transactional
    public String insertData(CakeRequestDto dto, String uuid) {
        LOG.info("persist cake");

        Cake cake = new Cake();
        cake.id = uuid;
        cake.tittle = dto.tittle;
        cake.description = dto.description;
        cake.rating = dto.rating;
        cake.image = dto.image;
        cake.activated = true;
        cake.createdAt =  new Timestamp(new Date().getTime());
        cake.updatedAt =  new Timestamp(new Date().getTime());
        cake.persist();

        return cake.id;
    }
}
