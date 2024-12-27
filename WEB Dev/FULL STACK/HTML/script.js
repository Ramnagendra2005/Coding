let arr = [90,58,65,50];
let res = arr.filter(function(element){
    return element>50;
})
let res1 = arr.filter(function(element){
    return element<90 && element >50;
})
console.log(res1);
let users = [
    {id:1,name:'Ram'},
    {id:2,name:'Praneeth'},
    {id:3,name:'Ashish'},
    {id:4,name:'Sathwik'},
];
let i = users.filter(function(element){
    return element.id<3;
})
console.log(i);
let res2 = arr.map(element=>element+10);
console.log(res2);
let res3 = arr.filter(element=>element>50);
console.log(res3);
arr.forEach((element,index)=>{
    console.log(element," ",index);
}) 
let a = arr.reduce((acc,ele)=>acc<ele?acc:ele)
console.log(a)
let ma = arr.reduce((acc,ele)=>acc>ele?acc:ele)
console.log(ma)
let sum=0;
arr.forEach((ele,index)=>{
    sum+=ele;
})
console.log(sum);

let more = users.reduce((acc,ele)=>ele.id>acc.id?ele:acc);
console.log(more)
let obj = {
    name:'RamNagendra',
    rollno:55,
    age:19
}
let v= Object.keys(obj);
console.log(v);
let x = Object.values(obj);
console.log(x);


class Person{
    age;
    name;
    constructor(a,b){
        this.name=a;
        this.age=b;
    }
}
let person1= new Person();
person1.name='Ram';
person1.age=19;
console.log(person1);
class Student{
    constructor(name,age,rollno){
        this.name=name;
        this.age=age;
        this.rollno=rollno;
    }
    getName(){
        return this.name;
    }
}
let std1 = new Student('Ramnagendra',19,55);
let std2 = new Student('Praneeth',19,54);
let std3 = new Student('Monish',19,37);
console.log(std1);
console.log(std2);
console.log(std3);
console.log(std1.getName());



// class Per {
//     constructor(name,age,mobile){
//         this.na=na;
//         this.age = age;
//         this.mobile = mobile;
//     }
// }



// class Stud extends Per{

//         constructor(id,marks){
//             super(na,age,mobile);
//             this.id = id;
//             this.marks = marks;
//         }

// }

// let p1 = new Per('ramnagendra',19,909);
// let st1 = new Stud(55,[10,20,30]);
// console.log(st1.name);
class Per {
    constructor(name, age, mobile) {
        this.name = name;
        this.age = age;
        this.mobile = mobile;
    }
}

class Stud extends Per {
    constructor(name, age, mobile, id, marks) {
        super(name, age, mobile); // Call the parent constructor
        this.id = id;
        this.marks = marks;
    }
}

let p1 = new Per('ramnagendra', 19, 909);
let st1 = new Stud('studentName', 20, 1234567890, 55, [10, 20, 30]);
console.log(st1.name); // This will now correctly output 'studentName'

let test = 'RamNagerndra';
console.log(typeof test);

let quote = 'Welcome to JavaScript';
console.log(quote.length);
console.log(quote.substring(2,5));
let quote2 = 'Hi my nsme is RamNagendra';
console.log(quote.charAt(0));

emp={
    a:10,
    b:20
}
//let copyEmp = Object.assign({},emp);
let copyEmp = {...emp};
console.log(copyEmp);