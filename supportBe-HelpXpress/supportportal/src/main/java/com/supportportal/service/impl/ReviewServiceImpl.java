package com.supportportal.service.impl;

import com.supportportal.domain.Review;
import com.supportportal.domain.User;
import com.supportportal.repository.ReviewRepository;
import com.supportportal.repository.UserRepository;
import com.supportportal.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String[] COMMENTS = {
        "Accesibilitatea aplicației este excelentă.",
        "Am întâmpinat dificultăți în navigarea prin meniu.",
        "Asistentul personal a fost de mare ajutor.",
        "Instrucțiunile oferite nu au fost suficient de clare pentru nevoile mele speciale.",
        "Interfața prietenoasă cu utilizatorul face utilizarea aplicației o plăcere.",
        "Aș aprecia mai multe opțiuni de personalizare pentru nevoile mele unice.",
        "Funcția de asistență vocală îmbunătățește semnificativ experiența de utilizare.",
        "Programările cu medicul sunt ușor de gestionat prin aplicație.",
        "Am întâmpinat unele probleme tehnice în timpul utilizării.",
        "Serviciul de suport pentru clienți este prompt și eficient.",
        "Apreciez accesul la diverse resurse și informații despre îngrijirea sănătății.",
        "Secțiunea de sfaturi practice este foarte utilă și bine structurată.",
        "Videoclipurile educative oferite sunt ușor de înțeles și relevante pentru condiția mea.",
        "Feedback-ul rapid de la medici prin intermediul aplicației este extrem de valoros.",
        "Funcționalitatea de a urmări progresul personal este motivantă și informativă.",
        "Apreciez diversitatea comunității și posibilitatea de a interacționa cu alți utilizatori.",
        "Opțiunile de filtrare pentru căutarea informațiilor sunt un pic limitate.",
        "Am fost încântat(ă) să găsesc atât de multe resurse accesibile într-un singur loc.",
        "Sugestiile personalizate pe baza condiției mele medicale sunt impresionante.",
        "Aș dori mai multe opțiuni de interacțiune directă cu specialiștii.",
        "Funcția de alertă pentru medicamente este incredibil de utilă.",
        "Secțiunea de întrebări frecvente răspunde la multe dintre nelămuririle mele.",
        "Instrucțiunile pentru exercițiile fizice adaptate sunt ușor de urmărit.",
        "Accesul la consultanță profesională prin intermediul aplicației este un mare avantaj.",
        "Funcționalitățile pentru gestionarea stresului și anxietății sunt bine gândite.",
    };

    @Override
    public void addBulkReviews() throws Exception {

    }
}
