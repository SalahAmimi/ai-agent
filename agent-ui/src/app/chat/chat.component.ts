import { Component } from '@angular/core';
import { HttpClient, HttpDownloadProgressEvent, HttpEventType } from '@angular/common/http';
import { NgClass, CommonModule } from '@angular/common';
import { MarkdownComponent } from 'ngx-markdown';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [NgClass, MarkdownComponent, FormsModule, CommonModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent {

  query: string = "";
  response: any;
  progress: boolean = false;
  messages: any[] = [];
  selectedFile: File | null = null;

  constructor(private http: HttpClient) { }

  askAgent() {
    const userQuery = this.query;
    if (!userQuery || this.progress) return;

    this.response = "";
    this.progress = true;
    this.messages.push({ role: 'user', content: userQuery, timestamp: new Date().toLocaleTimeString() });

    this.http.get("http://localhost:8080/askAgent/stream?query=" + this.query, { responseType: 'text' })
      .subscribe({
        next: resp => {
          this.response = resp;
        },
        error: err => {
          console.error(err);
        },
        complete: () => {
          this.progress = false;
          this.messages.push({ role: 'assistant', content: this.response, timestamp: new Date().toLocaleTimeString() });
          this.query = "";
        }
      });
  }

  triggerFileInput(): void {
    const fileInput = document.getElementById('fileInput') as HTMLElement;
    fileInput.click();
  }

  onFileAutoUpload(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      this.loadFile();
    }
  }

  loadFile(): void {
    if (!this.selectedFile) return;

    const formData = new FormData();
    formData.append("file", this.selectedFile);

    this.messages.push({
      role: 'user',
      content: `📎 Uploading file: ${this.selectedFile.name}`,
      timestamp: new Date().toLocaleTimeString()
    });

    this.http.post("http://localhost:8080/loadFile", formData, {
      responseType: 'text' as 'json'
    }).subscribe({
      next: () => {
        this.messages.push({
          role: 'assistant',
          content: `✅ File **${this.selectedFile?.name}** uploaded and indexed.`,
          timestamp: new Date().toLocaleTimeString()
        });
        this.selectedFile = null;
      },
      error: err => {
        console.error("Upload error: ", err);
        this.messages.push({
          role: 'assistant',
          content: `❌ Error uploading file: ${this.selectedFile?.name}`,
          timestamp: new Date().toLocaleTimeString()
        });
      }
    });
  }
}
