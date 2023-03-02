package akhid.development.service;

import akhid.development.dto.CakeRequestDto;
import akhid.development.model.postgres.Cake;
import akhid.development.util.BasicUtil;
import com.google.common.base.Strings;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.ws.rs.NotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@ApplicationScoped
public class CakeService {
    private static final Logger LOG = LoggerFactory.getLogger(CakeService.class);
    @PersistenceContext
    private EntityManager em;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public Map<String, Object> submit(CakeRequestDto dto) {

        String uuid = UUID.randomUUID().toString();
        Timestamp ldt = new Timestamp(new Date().getTime());
        Boolean active = true;

        // store to database
        insertData(dto, uuid, active, ldt);

        Map<String, Object> result = new HashMap<>();
        result.put("id", uuid);
        result.put("tittle", dto.tittle);
        result.put("description", dto.description);
        result.put("rating", dto.rating);
        result.put("image", dto.image);
        result.put("created_at", sdf.format(ldt));

        return result;

    }

    public Map<String, Object> findById(String id) throws Exception {
        LOG.trace("func find by id");

        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("BAD_REQUEST");
        }

        Query q = em.createNativeQuery(
                 "SELECT " +
                    "    c.id AS id,\n" +
                    "    c.tittle AS tittle,\n" +
                    "    c.description AS description,\n" +
                    "    c.rating AS rating,\n" +
                    "    c.image AS image,\n" +
                    "    c.active AS active,\n" +
                    "    c.created_at AS create_at\n" +
                    "FROM cake c\n" +
                    "WHERE c.id = :UUID"
        );
        q.setParameter("UUID", id);

        List result = BasicUtil.createListOfMapFromArray(
                q.getResultList(),
                "id", "tittle", "description", "rating", "image", "active", "create_at"
        );

        return result.size() > 0 ? (Map<String, Object>) result.get(0) : null;

    }

    public Map<String, Object> updateById(String id, CakeRequestDto dto) {
        Timestamp ldt = new Timestamp(new Date().getTime());

        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("BAD_REQUEST");
        }

        Cake cake = Cake.findById(id);
        if(cake == null) {
            throw new NotFoundException("NOT_FOUND");
        }

        // store to database
        updateData(id, dto, ldt);

        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("tittle", dto.tittle);
        result.put("description", dto.description);
        result.put("image", dto.image);
        result.put("updated_at", sdf.format(ldt));

        return result;
    }

    public Map<String, Object> deleteById(String id) {
        Timestamp ldt = new Timestamp(new Date().getTime());
        Boolean active = false;

        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("BAD_REQUEST");
        }

        Cake cake = Cake.findById(id);
        if(cake == null) {
            throw new NotFoundException("NOT_FOUND");
        }

        // store to database
        deleteData(id, active, ldt);

        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("active", active);
        result.put("deleted_at", sdf.format(ldt));

        return result;
    }

    @Transactional
    public void insertData (CakeRequestDto dto, String id, Boolean active, Timestamp ldt) {

        Cake cake = new Cake();
        cake.id = id;
        cake.tittle = dto.tittle;
        cake.description = dto.description;
        cake.rating = dto.rating;
        cake.image = dto.image;
        cake.active = active;
        cake.createdAt = ldt;
        cake.persist();

    }

    @Transactional
    public void updateData (String id, CakeRequestDto dto, Timestamp ldt) {

        Cake cake = Cake.findById(id);
        cake.tittle = dto.tittle;
        cake.description = dto.description;
        cake.rating = dto.rating;
        cake.image = dto.image;
        cake.updatedAt =  ldt;
        cake.persist();

    }

    @Transactional
    public void deleteData (String id, Boolean active, Timestamp ldt) {

        Cake cake = Cake.findById(id);
        cake.active = active;
        cake.deletedAt =  ldt;
        cake.persist();

    }

}
