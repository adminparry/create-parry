import * as fs from 'fs';
import * as path from 'path';
import { select1, projectPath } from './constants.mjs'

const __dirname = path.dirname(new URL(import.meta.url).pathname);
const log = console.log;



main().catch(console.error);
async function main() {

    const tmps = getAllTemplate();
    const choices = tmps.map(item => {
        return {
            name: item,
            value: item
        }
    });
    const { pp } = await projectPath();
    const { toppings } = await select1(choices);

    const pwd = path.resolve(process.cwd(), pp);

    toppings.forEach(item => {
        copy(path.resolve(__dirname, item), path.resolve(pwd, item.replace('template', 'project')))
    })

    

}

process.on('uncaughtException', (err) => {
    console.log(err);
    process.exit(1);
})



function getAllTemplate() {
    const ret = fs.readdirSync(__dirname).filter((file) => {
        const stat = fs.lstatSync(path.resolve(__dirname, file));
        return stat.isDirectory() && file.match(/^template-/);
    });
    return ret;
}

function copyDir(srcDir, destDir) {
    fs.mkdirSync(destDir, { recursive: true })

    for (const file of fs.readdirSync(srcDir)) {
        const srcFile = path.resolve(srcDir, file)
        const destFile = path.resolve(destDir, file)
        copy(srcFile, destFile)
    }
}

function copy(src, dest) {
    const stat = fs.statSync(src)
    if (stat.isDirectory()) {
      copyDir(src, dest)
    } else {
      fs.copyFileSync(src, dest)
    }
}