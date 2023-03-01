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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class CakeService {
    private static final Logger LOG = LoggerFactory.getLogger(CakeService.class);
    @Inject
    EntityManager em;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public Map<String, Object> submit(CakeRequestDto dto) {
        
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Timestamp ldt = new Timestamp(new Date().getTime());

        // store to database
        insertData(dto, uuid, ldt);

        Map<String, Object> result = new HashMap<>();
        result.put("id", uuid);
        result.put("tittle", dto.tittle);
        result.put("description", dto.description);
        result.put("image", dto.image);
        result.put("created_at", sdf.format(ldt));

        return result;
    }

    @Transactional
    public void insertData(CakeRequestDto dto, String uuid, Timestamp ldt) {

        Cake cake = new Cake();
        cake.id = uuid;
        cake.tittle = dto.tittle;
        cake.description = dto.description;
        cake.rating = dto.rating;
        cake.image = dto.image;
        cake.active = true;
        cake.createdAt =  ldt;
        cake.persist();

    }
}
