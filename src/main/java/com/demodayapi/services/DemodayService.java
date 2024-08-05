package com.demodayapi.services;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demodayapi.enums.DemodayStatusEnum;
import com.demodayapi.models.Demoday;
import com.demodayapi.repositories.DemodayRepository;

import jakarta.transaction.Transactional;

@Service
public class DemodayService {

    @Autowired
    private DemodayRepository demodayRepository;

    public Demoday saveDemoday(Demoday newDemoday) {
        if (newDemoday != null) {
            Demoday savedDemoday = this.demodayRepository.save(newDemoday);
            return this.demodayRepository.save(savedDemoday);

        }
        return null;
    }

    public boolean verifyAccEvalCriteriaAndNameExists(Demoday demoday) {
        System.out.println("ola ola ola hiiiiiiiiiiii : : :: ) ) ) ) aqui 6");
        if (demoday.getAccCriteriaDemoday() == null) {
            return true;
        } else {
            if (demoday.getEvalCriteriaDemoday() == null) {
                return true;
            } else {
                if (demoday.getName() == null) {
                    return true;
                }
            }
        }
        return false;

    }

    public List<Demoday> findAll() {
        return this.demodayRepository.findAll();
    }

    public boolean existsDemoday(int id) {
        Demoday existingDemoday = this.demodayRepository.findById(id);
        if (existingDemoday != null) {
            return true;
        }
        return false;
    }// precisa desse metodo?

    public boolean ValidateBiggestInitEndDate(Demoday demoday) {
        LocalDate today = LocalDate.now();
        System.out.println(today);
        System.out.println(demoday.getPhaseOneInit());
        if (demoday.getPhaseOneInit().isBefore(today)
                || demoday.getPhaseTwoInit() != null && demoday.getPhaseTwoInit().isBefore(today)
                || demoday.getPhaseThreeInit() != null && demoday.getPhaseThreeInit().isBefore(today)
                || demoday.getPhaseFourInit() != null && demoday.getPhaseFourInit().isBefore(today)) {
            System.out.println("ENTROU");
            return true;
        }
        // System.out.println(demoday.getPhaseOneInit().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        // System.out.println(demoday.getPhaseOneEnd().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        if (demoday.getPhaseOneInit().isBefore(demoday.getPhaseOneEnd()) && demoday.getPhaseTwoEnd() == null &&
                demoday.getPhaseFourEnd() == null && demoday.getPhaseFourEnd() == null) {
            return false;
        } else {
            if (demoday.getPhaseTwoEnd() != null &&
                    demoday.getPhaseThreeEnd() == null &&
                    demoday.getPhaseTwoInit() != null &&
                    demoday.getPhaseThreeInit() == null &&
                    demoday.getPhaseFourInit() == null &&
                    demoday.getPhaseFourEnd() == null &&
                    demoday.getPhaseOneInit().isBefore(demoday.getPhaseOneEnd()) &&
                    demoday.getPhaseOneEnd().isBefore(demoday.getPhaseTwoInit()) &&
                    demoday.getPhaseTwoInit().isBefore(demoday.getPhaseTwoEnd())) {

                return false;
            }
            if (demoday.getPhaseTwoEnd() != null && demoday.getPhaseThreeEnd() != null &&
                    demoday.getPhaseFourEnd() == null &&
                    demoday.getPhaseTwoInit() != null &&
                    demoday.getPhaseThreeInit() != null &&
                    demoday.getPhaseFourInit() == null &&
                    demoday.getPhaseOneInit().isBefore(demoday.getPhaseOneEnd()) &&
                    demoday.getPhaseOneEnd().isBefore(demoday.getPhaseTwoInit()) &&
                    demoday.getPhaseTwoInit().isBefore(demoday.getPhaseTwoEnd()) &&
                    demoday.getPhaseTwoEnd().isBefore(demoday.getPhaseThreeInit()) &&
                    demoday.getPhaseThreeInit().isBefore(demoday.getPhaseThreeEnd())) {

                return false;
            }
            if (demoday.getPhaseTwoEnd() != null &&
                    demoday.getPhaseThreeEnd() != null &&
                    demoday.getPhaseFourEnd() != null &&
                    demoday.getPhaseTwoInit() != null &&
                    demoday.getPhaseThreeInit() != null &&
                    demoday.getPhaseFourInit() != null &&
                    demoday.getPhaseOneInit().isBefore(demoday.getPhaseOneEnd()) &&
                    demoday.getPhaseOneEnd().isBefore(demoday.getPhaseTwoInit()) &&
                    demoday.getPhaseTwoInit().isBefore(demoday.getPhaseTwoEnd()) &&
                    demoday.getPhaseTwoEnd().isBefore(demoday.getPhaseThreeInit()) &&
                    demoday.getPhaseThreeInit().isBefore(demoday.getPhaseThreeEnd()) &&
                    demoday.getPhaseThreeEnd().isBefore(demoday.getPhaseFourInit()) &&
                    demoday.getPhaseFourInit().isBefore(demoday.getPhaseFourEnd())) {

                return false;
            }

        }
        return true;
    }

    public List<Demoday> getDemodayphaseFourIsNull() {

        return this.demodayRepository.getDemodayphaseFourNull();
    }

    public List<Demoday> getDemodayphaseFourIsNotNull() {

        return this.demodayRepository.getDemodayphaseFourNotNull();
    }

    public List<Demoday> getDemodayInProgress() {
        List<Demoday> demodayListphaseFourIsNull = this.getDemodayphaseFourIsNull();
        List<Demoday> demodayListphaseFourIsNotNull = this.getDemodayphaseFourIsNotNull();
        LocalDate dataAtual = LocalDate.now(); // Obtém a data atual
        for (Demoday demoday : demodayListphaseFourIsNull) {
            if (demoday.getPhaseFourEnd() == null) {
                System.out.println("demodayAtivo");
                return demodayListphaseFourIsNull;
            }
        }
        for (Demoday demoday : demodayListphaseFourIsNotNull) {
            if (demoday.getPhaseFourEnd().isAfter(dataAtual)) {
                System.out.println("demodayAtivo");
                return demodayListphaseFourIsNotNull; // Se a fase FourEnd estiver após a data atual, significa que o
                                                      // Demoday está em progresso
            }
        }

        return null;
    }

    public Demoday getActiveDemoday() {
        List<Demoday> demodays = this.getDemodayInProgress();
        if(demodays != null ){
            return demodays.get(0);
        }
        return null;
    }

    public List<Demoday> getAllDemodays(){
        return this.demodayRepository.findAll();
    }

    private Demoday geyDemodayById(int demodayId){
        return this.demodayRepository.findById(demodayId);
        
    }

    public Demoday getDemodayWithBiggestValuePhase1() {
        return this.demodayRepository.getPhase1();
    }

    public boolean isAfterDemodayPhase(Integer demodayId, DemodayStatusEnum demodayStatus){
        Demoday demoday = this.geyDemodayById(demodayId);
        if(demoday != null){
            LocalDate dateEndPhase;
            if(demodayStatus.equals(DemodayStatusEnum.PHASE1)) dateEndPhase = demoday.getPhaseOneEnd();
            else if(demodayStatus.equals(DemodayStatusEnum.PHASE2)) dateEndPhase = demoday.getPhaseTwoEnd();
            else if(demodayStatus.equals(DemodayStatusEnum.PHASE3)) dateEndPhase = demoday.getPhaseThreeEnd();
            else dateEndPhase = demoday.getPhaseFourEnd();

            if(dateEndPhase == null) return false;

            LocalDate dataAtual = LocalDate.now();

            if(dateEndPhase.isBefore(dataAtual)) return true;
            else return false;

        } else return true;

    }

    public DemodayStatusEnum getDemodayStatus(){
        List<Demoday> demodayList = this.getDemodayInProgress();
        if(demodayList != null){
            Demoday demoday = demodayList.get(0);
            System.out.println(demoday.getPhaseThreeEnd().toString());
            System.out.println(demoday.getPhaseFourInit().toString());
            LocalDate dataAtual = LocalDate.now();

            System.out.println(dataAtual.toString());
            if(
                demoday.getPhaseOneInit().isEqual(dataAtual) || demoday.getPhaseOneInit().isBefore(dataAtual) &&
                demoday.getPhaseOneEnd().isAfter(dataAtual) || demoday.getPhaseOneEnd().isEqual(dataAtual)
            ) return DemodayStatusEnum.PHASE1;

            if(demoday.getPhaseTwoInit() != null && demoday.getPhaseTwoEnd() != null){
                if(
                    demoday.getPhaseTwoInit().isEqual(dataAtual) || demoday.getPhaseTwoInit().isBefore(dataAtual) &&
                    demoday.getPhaseTwoEnd().isAfter(dataAtual) || demoday.getPhaseTwoEnd().isEqual(dataAtual)
                ) return DemodayStatusEnum.PHASE2;
            } else return null;

            if(demoday.getPhaseThreeInit() != null && demoday.getPhaseThreeEnd() != null){
                if(
                    demoday.getPhaseThreeInit().isEqual(dataAtual) || demoday.getPhaseThreeInit().isBefore(dataAtual) &&
                    demoday.getPhaseThreeEnd().isAfter(dataAtual) || demoday.getPhaseThreeEnd().isEqual(dataAtual)
                )
                    return DemodayStatusEnum.PHASE3;
            } else return null;

            if(demoday.getPhaseFourInit() != null && demoday.getPhaseFourEnd() != null){
                if(
                    demoday.getPhaseFourInit().isEqual(dataAtual) || demoday.getPhaseFourInit().isBefore(dataAtual) &&
                    demoday.getPhaseFourEnd().isAfter(dataAtual) || demoday.getPhaseFourEnd().isEqual(dataAtual)
                ) return DemodayStatusEnum.PHASE4;
            } else return null;

            return DemodayStatusEnum.FINISHEDPHASE;

        }
        return null;
    }

    public DemodayStatusEnum verifyphase1InProgress() {
        Demoday demoday = this.getDemodayWithBiggestValuePhase1();
        LocalDate dataAtual = LocalDate.now();
        if (demoday != null)
            if (demoday.getPhaseOneInit().isEqual(dataAtual) || demoday.getPhaseOneInit().isBefore(dataAtual) &&
                    demoday.getPhaseOneEnd().isAfter(dataAtual) ||
                    demoday.getPhaseOneEnd().isEqual(dataAtual)) {
                return DemodayStatusEnum.PHASE1;
            }

        return DemodayStatusEnum.FINISHEDPHASE;
    }

    @Transactional
    public void deleteDemodayById(int id) {
        Demoday demoday = demodayRepository.findById(id);
        if (demoday != null) {
             
            demodayRepository.delete(demoday);
        }
    }

    public Demoday findById(int id) {
        return demodayRepository.findById(id);
    }

}
