import lwc from '@lwc/rollup-plugin';
import replace from '@rollup/plugin-replace';

export default {
    input: 'src/main/resources/web/src/index.js',

    output: {
        file: 'src/main/resources/web/dist/index.js',
        format: 'esm',
    },

    plugins: [
        replace({
            preventAssignment: true,
            'process.env.NODE_ENV': JSON.stringify('development'),
        }),
        lwc(),
    ],
};

