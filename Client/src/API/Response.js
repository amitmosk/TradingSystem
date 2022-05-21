


export class Response {
    constructor(response) {
        this.value = response.value;
        this.message = response.message;
        this.was_exception = response.wasException;
    }
    static create(value, message, was_exception) {
        return new Response({
            value: value,
            message: message,
            was_exception: was_exception,

        })

    }
}