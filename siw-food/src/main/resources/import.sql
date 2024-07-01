insert into recipe (id, name, url_image, description, tempo, type) values(nextval('recipe_seq'), 'Pasta alla Carbonara', 'pasta_alla_carbonara.png', 'La Carbonara è un primo piatto romano con spaghetti, guanciale, pecorino, uova, pepe e sale. Un tripudio di sapori: il guanciale croccante, la cremosità delle uova, il pecorino saporito. Da provare!', 15,'primo');
insert into recipe (id, name, url_image, description, tempo, type) values(nextval('recipe_seq'), 'Pasta Cacio e Pepe', 'pasta_cacio_e_pepe.png', ' Un classico romano semplice ma gustoso: spaghetti conditi con pecorino romano grattugiato, pepe nero macinato fresco e acqua di cottura per creare una cremina densa.', 10,'primo');
insert into recipe (id, name, url_image, description, tempo, type) values(nextval('recipe_seq'), 'Pizza Margherita', 'pizza_margherita.png', ' Una icona della cucina italiana: pomodoro, mozzarella fior di latte, basilico fresco e olio extravergine di oliva su una base di pizza croccante' ,10,'primo');
insert into recipe (id, name, url_image, description, tempo, type) values(nextval('recipe_seq'), 'Pasta alla Norma', 'pasta_alla_norma.png', 'Un piatto siciliano ricco e saporito: melanzane fritte, ricotta salata, pomodoro e basilico su pasta al forno.', 15,'primo');
insert into recipe (id, name, url_image, description, tempo, type) values(nextval('recipe_seq'), 'Pasta Amatriciana', 'pasta_amatriciana.png', 'Un primo piatto romano con guanciale, pecorino romano, pomodoro, cipolla, peperoncino e vino bianco.', 10,'primo');
insert into recipe (id, name, url_image, description, tempo, type) values(nextval('recipe_seq'), 'Bistecca di Manzo', 'bistecca.png', 'Un secondo piatto classico e versatile: carne di manzo cotta alla griglia o alla piastra, servita con i tuoi contorni preferiti.',10, 'secondo');
insert into recipe (id, name, url_image, description, tempo, type) values(nextval('recipe_seq'), 'Pollo alla Cacciatora', 'pollo_alla_cacciatora.png', 'Pollo cucinato con pomodori, cipolle, vino bianco, olive e aromi come rosmarino e alloro.',20, 'secondo');
insert into recipe (id, name, url_image, description, tempo, type) values(nextval('recipe_seq'), 'Tiramisù', 'tiramisu.png', 'Strati di savoiardi imbevuti di caffè e mascarpone, spolverati con cacao.',40, 'dolce');
insert into recipe (id, name, url_image, description, tempo, type) values(nextval('recipe_seq'), 'Panna Cotta', 'panna_cotta.png', 'Dolce a base di panna, zucchero e gelatina, spesso servito con coulis di frutti di bosco o caramello.',20, 'dolce');
insert into recipe (id, name, url_image, description, tempo, type) values(nextval('recipe_seq'), 'Bruschetta al Pomodoro', 'bruschetta.png', 'Fette di pane tostato condite con pomodoro fresco, aglio, basilico e olio di oliva.',5, 'antipasto');
insert into recipe (id, name, url_image, description, tempo, type) values(nextval('recipe_seq'), 'Caprese', 'caprese.png', ' Insalata semplice di pomodori, mozzarella di bufala, basilico fresco e olio di oliva.',10, 'antipasto');
insert into recipe (id, name, url_image, description, tempo, type) values(nextval('recipe_seq'), 'Branzino al Sale', 'branzino_al_sale.png', 'La cottura al sale preserva il sapore naturale del pesce e offre una preparazione elegante e gustosa, perfetta per chi desidera un pasto delizioso e senza complicazioni.', 20, 'secondo');


insert into cook (id, name, surname, url_image, birth) values(nextval('cook_seq'), 'Carlo', 'Cracco', 'carlo_cracco.png', '8-10-1965');
insert into cook (id, name, surname, url_image, birth) values(nextval('cook_seq'), 'Massimo', 'Bottura', 'massimo_bottura.png', '30-09-1962');
insert into cook (id, name, surname, url_image, birth) values(nextval('cook_seq'), 'Joe ', 'Bastianich', 'joe_bastianich.png', '17-09-1968');
insert into cook (id, name, surname, url_image, birth) values(nextval('cook_seq'), 'Enrico ', 'Crippa', 'enrico_crippa.png', '25-11-1971');
insert into cook (id, name, surname, url_image, birth) values(nextval('cook_seq'), 'Antonino ', 'Cannavacciuolo', 'antonino_cannavacciuolo.png', '16-04-1975');
insert into cook (id, name, surname, url_image, birth) values(nextval('cook_seq'), 'Bruno', 'Barbieri', 'bruno_barbieri.png','12-01-1962');
insert into cook (id, name, surname, url_image, birth) values(nextval('cook_seq'), 'Alessandro', 'Borghese', 'alessandro_borghese.png', '19-11-1976');



