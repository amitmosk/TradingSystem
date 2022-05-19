


export class Response {
    constructor(response) {
        this.value = response.value;
        this.message = response.message
    }
    static create(value, message) {
        return new Response({
            value: value,
            message: message,

        })

    }
}