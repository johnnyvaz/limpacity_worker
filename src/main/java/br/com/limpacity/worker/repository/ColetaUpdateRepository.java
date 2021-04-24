package br.com.limpacity.worker.repository;

import br.com.limpacity.worker.model.ColetaQrCode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Repository
public class ColetaUpdateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void updateWithQuery(ColetaQrCode coleta) {
        Date data = new Date();
        entityManager.createNativeQuery("UPDATE coleta_qrcode SET integration_status = ? ," +
                " update_date = ?, data_ultimo_email = ?, qtde_not_email = ? WHERE id = ? ")
                .setParameter(1, "R")
                .setParameter(2, data)
                .setParameter(3, data)
                .setParameter(4, coleta.getQtdeNotEmail()+1)
                .setParameter(5, coleta.getId())
                .executeUpdate();
    }
}
