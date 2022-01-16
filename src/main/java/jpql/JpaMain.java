package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpafirst");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //code
        try {
            Member member = new Member();
            member.setUsername("관리자");
            member.setType(MemberType.ADMIN);
            em.persist(member) ;

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setType(MemberType.ADMIN);

            em.persist(member2) ;

            em.flush();
            em.clear();

            String query = "select group_concat(m.username)  from Member m";
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for (String str : result) {
                System.out.println("str = " + str);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

}
