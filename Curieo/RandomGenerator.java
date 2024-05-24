package Curieo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class RandomGenerator {
    private static final float MIN_SEV = 1;
    private static final float MAX_SEV = 100;
    private static final int DAYS_IN_FUTURE = 360;

    private static final List<String> errorCodes = List.of(
        "INTERNAL_SERVER_ERROR",
        "BAD_REQUEST",
        "SOMETHING",
            "SOMETHING_ELSE",
            "ABCD",
            "EFGH"
    );

    private static final List<Character> queryIndices = List.of(
        '1',
        '2',
        '3',
        '4'
    );

    private static final List<String> queryIds = List.of(
        "BEFORE",
        "AFTER"
    );;

    private static Random random = new Random();

    private float generateRandomFloat(){
        float returnValue = RandomGenerator.MIN_SEV + (RandomGenerator.MAX_SEV - RandomGenerator.MIN_SEV)*random.nextFloat();
        return returnValue;
    }

    private String generateRandomString(){
        int randomIndex = random.nextInt(errorCodes.size());
        return errorCodes.get(randomIndex);
    }

    private Character generateRandomIndex(){
        int randomIndex = random.nextInt(queryIndices.size());
        return queryIndices.get(randomIndex);
    }

    private String generateRandomId(){
        int randomIndex = random.nextInt(queryIds.size());
        return queryIds.get(randomIndex);
    }

    private long generateRandomTs(){
        long now = Instant.now().toEpochMilli();
        long future = Instant.now().plus(DAYS_IN_FUTURE, ChronoUnit.DAYS).toEpochMilli();

        return now + (long) (random.nextDouble() * (future - now));
    }

    public String generateRandomQuery(){
        Character index = this.generateRandomIndex();
        StringBuilder query = new StringBuilder();

        if(index=='1'){
            query.append("1 ");
            query.append(this.generateRandomTs()+";"+this.generateRandomString()+";"+generateRandomFloat());
            return query.toString();
        }

        else{
            query.append(index + " ");

            if(index=='2'){
                query.append(this.generateRandomString());
                return query.toString();
            }

            else{
                String id = this.generateRandomId();

                if(index=='3'){
                    query.append(id + " " + this.generateRandomTs());
                }

                else{
                    query.append(id + " " + this.generateRandomString()+" "+this.generateRandomTs());
                }

                return query.toString();
            }
        }
    }

    public List<String> generateListQueries(int len){
        ArrayList<String> arr = new ArrayList<>();

        for(int i=0;i<len;i++)
            arr.add(this.generateRandomQuery());
        
        return arr;
    }
}
