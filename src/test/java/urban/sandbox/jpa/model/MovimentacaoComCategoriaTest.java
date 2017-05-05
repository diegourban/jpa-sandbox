package urban.sandbox.jpa.model;


import org.junit.Test;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;

public class MovimentacaoComCategoriaTest {

    @Test
    public void devePersistir() {
        Categoria categoria1 = new Categoria("viagem");
        Categoria categoria2 = new Categoria("negócios");

        Conta conta = new Conta();
        conta.setTitular("Diego");
        conta.setBanco("Banco do Brasil");
        conta.setAgencia("123");
        conta.setNumero("456");

        Movimentacao movimentacao1 = new Movimentacao();
        movimentacao1.setData(Calendar.getInstance());
        movimentacao1.setDescricao("Viagem a são paulo");
        movimentacao1.setTipo(TipoMovimentacao.SAIDA);
        movimentacao1.setValor(new BigDecimal("100.00"));
        movimentacao1.setCategorias(Arrays.asList(categoria1, categoria2));
        movimentacao1.setConta(conta);

        Movimentacao movimentacao2 = new Movimentacao();
        movimentacao2.setData(Calendar.getInstance());
        movimentacao2.setDescricao("Viagem ao rio de janeiro");
        movimentacao2.setTipo(TipoMovimentacao.SAIDA);
        movimentacao2.setValor(new BigDecimal("300.00"));
        movimentacao2.setCategorias(Arrays.asList(categoria1, categoria2));
        movimentacao1.setConta(conta);

        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        em.persist(conta);
        em.persist(categoria1);
        em.persist(categoria2);
        em.persist(movimentacao1);
        em.persist(movimentacao2);

        em.getTransaction().commit();
        em.close();
    }
}
