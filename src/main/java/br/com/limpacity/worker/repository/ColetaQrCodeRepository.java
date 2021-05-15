package br.com.limpacity.worker.repository;

import br.com.limpacity.worker.model.ColetaQrCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColetaQrCodeRepository extends JpaRepository<ColetaQrCodeModel, Long> {

    @Query(" select c from ColetaQrCodeModel c where " +
            " c.ativo = true and " +
            " c.integrationStatus = 'N'")
    List<ColetaQrCodeModel> findAllColetasOpen();

//
//        @Transactional
//        @Modifying
//        @Query("UPDATE ColetaQrCodeModel c SET c.qtdeNotEmail = c.qtdeNotEmail+1 WHERE c.id = :id ")
//        void updateWithQuery(@Param("id") Long id);




//    @PersistenceContext
//    private EntityManager entityManager;

//    @Transactional
//    public boolean updateWithQuery(ColetaQrCodeModel coleta) {
//        Date data = new Date();
//        try {
//            entityManager.createNamedQuery("UPDATE coleta_qrcode SET integration_status = ? ," +
//                " update_date = ?, data_ultimo_email = ?," +
//                    " qtde_not_email = ? WHERE id = ? ")
//                    .setParameter(1, "R")
//                    .setParameter(2, data)
//                    .setParameter(3, data)
//                    .setParameter(4, coleta.getQtdeNotEmail() + 1)
//                    .setParameter(5, coleta.getId())
//                    .executeUpdate();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
}
