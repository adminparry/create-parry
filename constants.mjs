
import inquirer from 'inquirer';


export const select1 = async function (choices) {

    return inquirer
        .prompt([
            {
                type: 'checkbox',
                message: 'Select Project',
                name: 'toppings',
                choices,
                // choices: [
                //     new inquirer.Separator(' = The Meats = '),
                //     {
                //         name: 'Pepperoni',
                //     },
                //     {
                //         name: 'Ham',
                //     },
                //     {
                //         name: 'Ground Meat',
                //     },
                //     {
                //         name: 'Bacon',
                //     },
                //     new inquirer.Separator(' = The Cheeses = '),
                //     {
                //         name: 'Mozzarella',
                //         checked: true,
                //     },
                //     {
                //         name: 'Cheddar',
                //     },
                //     {
                //         name: 'Parmesan',
                //     },
                //     new inquirer.Separator(' = The usual ='),
                //     {
                //         name: 'Mushroom',
                //     },
                //     {
                //         name: 'Tomato',
                //     },
                //     new inquirer.Separator(' = The extras = '),
                //     {
                //         name: 'Pineapple',
                //     },
                //     {
                //         name: 'Olives',
                //         disabled: 'out of stock',
                //     },
                //     {
                //         name: 'Extra cheese',
                //     },
                // ],
                validate(answer) {
                    if (answer.length === 0) {
                        return 'You must choose at least one topping.';
                    }

                    return true;
                },
            },
        ])
        
}

export const projectPath = async function() {
    return inquirer.prompt({
        type: 'input',
        name: 'pp',
        message: 'Where do you want to create your project?',
        default: process.cwd(),
    })
}