
# Relationship
Relationship between Activity and AsyncTask



Як зберегти зв'язок між Activity  та AsynkTask?

Для реалізації такого зв'язок  використаємо android.app.Fragment та його метод setRetainInstance(true) . 

для початку ознайомтесь з цим https://drive.google.com/open?id=0B9E-fi6urGxaOER3aUh6TzhvMkE

Отже у нас є два основних класи MainActivity та MainFragment

<strong>MainActivity</strong> містить:

- Button “Start” -  викликає метод  start() в класі MainFragment
- Button “Stop” -  викликає метод  stop() в класі MainFragment
- ProgressBar  -   показується при запуску довготривалого процесу doInBackground(), та зупиняються по його завершенню. 
- метод showProgress()  - запускає та зупиняє ProgressBar взалежності від стану doInBackground()  виконується він чи ні.
- метод getMainFragment()  - перевіряє чи існує екземпляр класу  MainFragment 
    якщо існує,  то присвоює цей об'єкт змінній mainFragment;	
    якщо ні, то створює новий екземпляр класу  MainFragment;  присвоює його змінній mainFragment; та реєструє його у FragmentManager.

<strong>Важливо:</strong>

Оскільки при кожному повороті екрану створюється новий екземпляр класу <strong>MainActivity</strong>, а екземпляр класу  <strong>MainFragment</strong> залишається сталим завдяки методу <strong>setRetainInstance(true)</strong>, то сталому об'єкту необхідно знати, що був створений новий Activity і мати до нього доступ. Для цього в методі <strong>onCreate()</strong>  отримуємо наш сталий екземпляр класу <strong>MainFragment</strong> і викликаємо його метод <strong>link(this)</strong> куди передаємо, щойно створене Activity, тим сам ми інформуємо <strong>MainFragment</strong>, що йому необхідно працювати з новим Activity.  
<hr>
<strong>MainFragment</strong> унаслідується від <strong>android.app.Fragment</strong> та містить вложений клас <strong>FragmentTask</strong>,  що в свою чергу унаслідується від   <strong>android.osAsyncTask</strong>.

<strong>MainFragment</strong> містить:
- змінну isWork - що вказує на стан класу <strong>FragmentTask</strong>, тобто - виконується його метод <strong>doInBackground()</strong> в даний момет часу, чи ні. (true - запущений, false - ні)
- метод link() - отримує Activity від класу <strong>MainActivity</strong>.
- метод start() - запускає процес <strong>doInBackground()</strong>  в класі <strong>FragmentTask</strong>  і встановлює мітку isWork = true - процес зупущено
- метод stop() - зупинсяє процес <strong>doInBackground()</strong> в класі <strong>FragmentTask</strong>  і встановлює мітку isWork = false - процес не активний 

<strong>Важливо:</strong>

Оскільки клас <strong>MainFragment</strong> унаслідується від <strong>android.app.Fragment</strong> то в методі  <strong>onCreate()</strong> використаємо властивість даного класу, а саме  <strong>setRetainInstance(true)</strong> - що забороняє  фрагменту створювати новий екземпляр класу при повороті основного Activity. Отже якщо при кожному повороті екрану у нас буде новий екземпляр класу <strong>MainActivity</strong>, а даний фрагмент завжди буде сталим і пам'ятатиме свій стан в якому він знаходиться. 



