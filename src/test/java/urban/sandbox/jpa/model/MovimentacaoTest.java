package urban.sandbox.jpa.model;

import org.junit.Before;
import org.junit.Test;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Calendar;

public class MovimentacaoTest {
    
    @Before
    public void before() {
        EntityManager manager = new JPAUtil().getEntityManager();

        manager.getTransaction().begin();

        manager.getTransaction().commit();

        manager.close();
    }

    @Test
    public void devePersistirMovimentacao() {
        // transient
        Conta conta = new Conta();
        conta.setTitular("Diego");
        conta.setBanco("Banco do Brasil");
        conta.setAgencia("123");
        conta.setNumero("456");

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setData(Calendar.getInstance());
        movimentacao.setDescricao("churrascaria");
        movimentacao.setTipo(TipoMovimentacao.SAIDA);
        movimentacao.setValor(new BigDecimal("200.00"));
        movimentacao.setConta(conta);

        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        em.persist(conta);
        em.persist(movimentacao); // managed

        em.getTransaction().commit();
        em.close();
    }
}
