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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("teamA");
            member.setAge(10);
            member.changeTeam(team);

            em.persist(member) ;

            em.flush();
            em.clear();

            String query = "select m ,(select m1.age from Member as m1) as m2 from Member m left outer join Team t on m.username = t.name";
            List<Integer> result = em.createQuery(query, Integer.class)
                    .getResultList();

            System.out.println("result = " + result.size());
            for (Integer member1 : result) {
                System.out.println("member1 = " + member1);
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
