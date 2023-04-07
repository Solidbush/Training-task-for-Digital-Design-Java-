# Вступительное задание
---
## Описание:
> Реализация некоторого подобия языка управления данными в коллекции. Коллекция данных в данной задаче это структура представляющая собой некоторую таблицу данных, в которой есть наименования колонок и каждая строчка в таблице это элемент коллекции.

---

## Формат коллекции:
> Коллекция представляет из себя таблицу в виде **List<Map<String,Object>>**. Где **List** это список строк в таблице. **Map** это значения в колонках, где ключом **Map** является наименование колонки в виде строки, а значением **Map** является значение в ячейке таблицы (допустимые типы значений в ячейках: **Long**, **Double**, **Boolean**, **String**).

---

## Основные команды:
1. **INSERT**  - вставка элемента в коллекцию
2. **UPDATE**  изменение элемента в коллекции
3. **DELETE** - удаление элемента из коллекции
4. **SELECT** - поиск элементов в коллекции

---

## Особенности реализации:
* ### **Требования к формату ввода:**
 + Названия колонок таблицы и строковые значения указываются в одинарных кавычках. (**'**)
 + Значения полей указываются через **","**.
 + Поля в условие указываются через логические операторы **or**/**and**.
 + Команды: **Select**, **Insert**, **Delete**, **Update** - **регистронезависимые.**
 + Ключевые слова: **Where**, **Values** - **регистронезависимые.**
 + Логические операторы: **or**, **and**, **like**, **ilike** - **регистрозависимые.**
 + Названия колонок: **Id**, **lastName**, **cost**, **active**, **age** - **регистронезависимые.**
* ### **Формат ввода команд:**
  * **Insert:** Insert values [значения_полей (через через запятую **","**)]
    > Примеры:
    > * Insert values 'lastName' = 'Егоров', 'id'=2, 'age'=40, 'active'=false
    > * Insert values 'lastName' = 'Федоров', 'id'=3, 'age'=30, 'active'=False, 'cost'=45.8
    > * Insert values 'id'=1, 'age'=46, 'active'=true
  * **Select:** Select / Select where [условие (через логические операторы **or**/**and**)]
    > Примеры:
    > * Select
    > * Select where 'lastName'='Федоров'
    > * Select where 'lastName' ilike '%ДОР%' and 'active'=true or 'id' = 1 and 'age'>30 or 'cost'!=0
  * **Delete:** Delete where [условие (через логические операторы **or**/**and**)]
    > Примеры:
    > * Delete where 'cost' >= 45
    > * Delete where 'lastName' ilike 'Егоров%' or 'id'=2 and 'age'<40
    > * Delete where 'lastName' ilike '%Гор%'
  * **Update:** Update values [значения_полей (через через запятую **","**)] / Update values [значения_полей (через через запятую **","**)] where [условие (через логические операторы **or**/**and**)]
    > Примеры:
    > * Update values 'id'=2 where 'id'=3 or 'id'=1
    > * Update values 'id'=0, 'id'=1
    > * Update values 'cost'=35.8, 'lastName'='Петров' where 'id'>1 and 'active'=true or 'active'=false

---
## Тесты:
### Входные значения:
* INSERT VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=30, 'active'=False, 'cost'=45.8
* INSERT VALUES 'lastName' = 'Егоров1' , 'id'=2, 'age'=40, 'active'=false
* INSERT VALUES 'lastName' = '1Егоров1' , 'id'=1, 'age'=46, 'active'=true
* INSERT VALUES 'lastName' = '1Егоров' , 'id'=0, 'age'=48, 'active'=true, 'cost'=36
* INSERT VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=30, 'active'=False, 'cost'=45.8
* INSERT VALUES 'lastName' = 'Егоров1' , 'id'=2, 'age'=40, 'active'=false
* INSERT VALUES 'lastName' = '1Егоров1' , 'id'=1, 'age'=46, 'active'=true
* INSERT VALUES 'lastName' = '1Егоров' , 'id'=0, 'age'=48, 'active'=true, 'cost'=36

---

### Запросы:
1. Select
    * Вывод:
        > {lastName=Федоров, cost=45.8, active=false, id=3, age=30}  
	    {lastName=Егоров1, cost=null, active=false, id=2, age=40}  
	    {lastName=1Егоров1, cost=null, active=true, id=1, age=46}  
	    {lastName=1Егоров, cost=36.0, active=true, id=0, age=48}  
	    {lastName=Федоров, cost=45.8, active=false, id=3, age=30}  
	    {lastName=Егоров1, cost=null, active=false, id=2, age=40}  
	    {lastName=1Егоров1, cost=null, active=true, id=1, age=46}  
	    {lastName=1Егоров, cost=36.0, active=true, id=0, age=48}  
2. select where 'lastName'='Федоров'
    * Вывод:
	    > {lastName=Федоров, cost=45.8, active=false, id=3, age=30}  
	    {lastName=Федоров, cost=45.8, active=false, id=3, age=30}
3. Select where 'lastName' ilike '%ДОР%' and 'active'=true or 'id' = 1 and 'age'>30
    * Вывод:
	    > {lastName=1Егоров1, cost=null, active=true, id=1, age=46}  
	    {lastName=1Егоров1, cost=null, active=true, id=1, age=46}
4. Select where 'lastName' ilike '%ДОР%' and 'active'=true or 'id' = 1 and 'age'>30 or 'cost'!=0
    * Вывод:
	    > {lastName=Федоров, cost=45.8, active=false, id=3, age=30}  
	    {lastName=Егоров1, cost=null, active=false, id=2, age=40}  
	    {lastName=1Егоров1, cost=null, active=true, id=1, age=46}  
	    {lastName=1Егоров, cost=36.0, active=true, id=0, age=48}  
	    {lastName=Федоров, cost=45.8, active=false, id=3, age=30}  
	    {lastName=Егоров1, cost=null, active=false, id=2, age=40}  
	    {lastName=1Егоров1, cost=null, active=true, id=1, age=46}  
	    {lastName=1Егоров, cost=36.0, active=true, id=0, age=48}
5. delete where 'cost'=0
	* Вывод:
        > ----
6. delete where 'cost' >= 45
    * Вывод:
	    > {lastName=Федоров, cost=45.8, active=false, id=3, age=30}  
	    {lastName=Федоров, cost=45.8, active=false, id=3, age=30}
7. delete where 'lastName' ilike 'Егоров%' or 'id'=2 and 'age' <40
   * Вывод:
	    > {lastName=Егоров1, cost=null, active=false, id=2, age=40}  
	    {lastName=Егоров1, cost=null, active=false, id=2, age=40}
8. update values 'cost'=35 where 'cost'!=0
   * Вывод:
	    > {lastName=1Егоров1, cost=35.0, active=true, id=1, age=46}    
	    {lastName=1Егоров, cost=35.0, active=true, id=0, age=48}  
	    {lastName=1Егоров1, cost=35.0, active=true, id=1, age=46}  
	    {lastName=1Егоров, cost=35.0, active=true, id=0, age=48}  
9.  update values 'cost'=20 where 'active'=true and 'age'<46 or 'cost'>20
     * Вывод:
	    > {lastName=1Егоров1, cost=20.0, active=true, id=1, age=46}  
	    {lastName=1Егоров, cost=20.0, active=true, id=0, age=48}  
	    {lastName=1Егоров1, cost=20.0, active=true, id=1, age=46}  
	    {lastName=1Егоров, cost=20.0, active=true, id=0, age=48}  
10. update values 'cost'=10 where 'active'=true and 'id'=1 or 'age'>40 and 'cost'=20
     * Вывод:
	    > {lastName=1Егоров1, cost=10.0, active=true, id=1, age=46}  
	    {lastName=1Егоров, cost=10.0, active=true, id=0, age=48}  
	    {lastName=1Егоров1, cost=10.0, active=true, id=1, age=46}  
	    {lastName=1Егоров, cost=10.0, active=true, id=0, age=48}
11. delete where 'lastname' ilike '%ГОРОВ'
    * Вывод:
	    > {lastName=1Егоров, cost=10.0, active=true, id=0, age=48}  
	    {lastName=1Егоров, cost=10.0, active=true, id=0, age=48}
12. delete where 'lastname' like '1Егоров%' and 'id'=1
     * Вывод:
	    > {lastName=1Егоров1, cost=10.0, active=true, id=1, age=46}  
	    {lastName=1Егоров1, cost=10.0, active=true, id=1, age=46}
13. INSERT VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=30, 'active'=False, 'cost'=45.8
    * Вывод:
	    > {lastName=Федоров, cost=45.8, active=false, id=3, age=30}
14. update values 'lastname' = null, 'id'=null, 'age' = null, 'active'=null
    * Вывод:
	    > {lastName=null, cost=45.8, active=null, id=null, age=null}
15. update values 'lastname'=null
    * Вывод:
	    > {lastName=null, cost=45.8, active=null, id=null, age=null}
16. delete where 'cost'>-1
    * Вывод:
	    > {lastName=null, cost=45.8, active=null, id=null, age=null}

---

### Задание выполнил [Гаврилов Алексей](https://github.com/Solidbush)
